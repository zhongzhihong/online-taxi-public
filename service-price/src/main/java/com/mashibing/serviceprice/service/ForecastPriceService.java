package com.mashibing.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import com.mashibing.serviceprice.feign.ServiceMapClient;
import com.mashibing.serviceprice.mapper.PriceRuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class ForecastPriceService {

    @Autowired
    ServiceMapClient serviceMapClient;

    @Autowired
    PriceRuleMapper priceRuleMapper;


    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude,
                                        String cityCode, String vehicleType) {

        log.info("出发地经度：" + depLongitude);
        log.info("出发地纬度：" + depLatitude);
        log.info("目的地经度：" + destLongitude);
        log.info("目的地纬度：" + destLatitude);

        log.info("调用地图服务，查询距离和时长");
        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        forecastPriceDTO.setDestLatitude(destLatitude);
        ResponseResult<DirectionResponse> driving = serviceMapClient.direction(forecastPriceDTO);
        Integer distance = driving.getData().getDistance();
        Integer duration = driving.getData().getDuration();
        log.info("出发地和目的地距离为：" + distance + "，需要的时长为：" + duration);

        log.info("读取计价规则");
        HashMap<String, Object> map = new HashMap<>();
        map.put("city_code", cityCode);
        map.put("vehicle_type", vehicleType);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("city_code", cityCode);
        queryWrapper.eq("vehicle_type", vehicleType);
        queryWrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        if (priceRules.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(), CommonStatusEnum.PRICE_RULE_EMPTY.getValue());
        }
        PriceRule priceRule = priceRules.get(0);
        log.info("priceRules结果为：" + priceRules);

        log.info("根据距离、时长和计价规则计算价格");
        double price = getPrice(distance, duration, priceRule);

        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(price);
        forecastPriceResponse.setCityCode(cityCode);
        forecastPriceResponse.setVehicleType(vehicleType);
        forecastPriceResponse.setFareType(priceRule.getFareType());
        forecastPriceResponse.setFareVersion(priceRule.getFareVersion());
        return ResponseResult.success(forecastPriceResponse);
    }

    //根据距离、时长和计价规则，计算最终价格
    public double getPrice(Integer distance, Integer duration, PriceRule priceRule) {
        BigDecimal price = new BigDecimal(0);

        // 起步价
        Double startFare = priceRule.getStartFare();
        BigDecimal startFareBigDecimal = new BigDecimal(startFare);
        price = price.add(startFareBigDecimal);

        // 里程费（注意这里需要注意起步里程和真正走的里程）
        // 总里程
        BigDecimal distanceBigDecimal = new BigDecimal(distance);  //单位：米
        BigDecimal distanceMileBigDecimal = distanceBigDecimal.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);  //单位：千米
        // 起步里程
        Integer startMile = priceRule.getStartMile();
        BigDecimal startMileBigDecimal = new BigDecimal(startMile);
        // 真正走的里程
        double distanceSubtract = distanceMileBigDecimal.subtract(startMileBigDecimal).doubleValue();
        // 真正收费的里程数：因为会出现打车的里程<起步里程，这时相减会得到负数，需要转成正数
        double mile = distanceSubtract < 0 ? 0 : distanceSubtract;
        BigDecimal mileBigDecimal = new BigDecimal(mile);
        // 计程单价
        Double unitPricePerMile = priceRule.getUnitPricePerMile();
        BigDecimal unitPricePerMileBigDecimal = new BigDecimal(unitPricePerMile);
        // 计算里程总价，setScale()：保留小数点后两位，并且四舍五入
        BigDecimal mileFare = mileBigDecimal.multiply(unitPricePerMileBigDecimal).setScale(2, BigDecimal.ROUND_HALF_UP);
        price = price.add(mileFare);

        // 时长费
        BigDecimal time = new BigDecimal(duration);  //单位：秒
        // 时长分钟数
        BigDecimal timeBigDecimal = time.divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP);  //单位：分钟
        // 时长单价
        Double unitPricePerMinute = priceRule.getUnitPricePerMinute();
        BigDecimal unitPricePerMinuteBigDecimal = new BigDecimal(unitPricePerMinute);
        // 计算时长总价
        BigDecimal timeFare = timeBigDecimal.multiply(unitPricePerMinuteBigDecimal);
        price = price.add(timeFare);

        return price.doubleValue();
    }

//    public static void main(String[] args) {
//        PriceRule priceRule = new PriceRule();
//        priceRule.setStartFare(10.0);
//        priceRule.setStartMile(3);
//        priceRule.setUnitPricePerMile(1.8);
//        priceRule.setUnitPricePerMinute(0.5);
//
//        System.out.println(getPrice(6500, 1800, priceRule));
//    }

}
