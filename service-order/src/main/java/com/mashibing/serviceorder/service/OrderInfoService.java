package com.mashibing.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.OrderConstants;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.internalcommon.response.OrderDriverResponse;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import com.mashibing.serviceorder.feign.ServiceDriverUserClient;
import com.mashibing.serviceorder.feign.ServiceMapClient;
import com.mashibing.serviceorder.feign.ServicePriceClient;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderInfoService {

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    ServicePriceClient servicePriceClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    ServiceMapClient serviceMapClient;

    // 测试代码
    public ResponseResult addInfo() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setAddress("广东省广州市");
        orderInfoMapper.insert(orderInfo);
        return ResponseResult.success("");
    }

    // 插入订单
    public ResponseResult addOrder(OrderRequest orderRequest) {

        //根据城市编码查询该城市是否有可用司机
        ResponseResult<Boolean> hasAvailableDriver = serviceDriverUserClient.hasAvailableDriver(orderRequest.getAddress());
        System.out.println("hasAvailableDriver的结果为：" + hasAvailableDriver);
        if (!hasAvailableDriver.getData()) {
            return ResponseResult.fail(CommonStatusEnum.CITY_DRIVER_EMPTY.getCode(), CommonStatusEnum.CITY_DRIVER_EMPTY.getValue());
        }

        //需要判断计价规则的版本是否为最新的
        ResponseResult<Boolean> latestPrice = servicePriceClient.isLatestPrice(orderRequest.getFareType(), orderRequest.getFareVersion());
        if (!(latestPrice.getData())) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode(), CommonStatusEnum.PRICE_RULE_CHANGED.getValue());
        }

        // 当前设备是黑名单设备不允许下单
        if (isBlackDevice(orderRequest))
            return ResponseResult.fail(CommonStatusEnum.DEVICE_IS_BLACK.getCode(), CommonStatusEnum.DEVICE_IS_BLACK.getValue());

        // 当前下单的城市和计价规则不存在，即不提供叫车服务
        if (!ifExist(orderRequest)) {
            return ResponseResult.fail(CommonStatusEnum.CITY_SERVICE_NOT_SERVICE.getCode(), CommonStatusEnum.CITY_SERVICE_NOT_SERVICE.getValue());
        }

        // 乘客有正在进行的订单不允许下单
        if (IsPassengerOrderGoingOn(orderRequest.getPassengerId()) > 0) {
            return ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON.getCode(), CommonStatusEnum.ORDER_GOING_ON.getValue());
        }

        //需要插入的信息
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest, orderInfo);
        orderInfo.setOrderStatus(OrderConstants.ORDER_START);
        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);

        orderInfoMapper.insert(orderInfo);
        //进行派单
        dispatchRealTimeOrder(orderInfo);

        return ResponseResult.success("");
    }

    // 实时订单派送逻辑代码
    public void dispatchRealTimeOrder(OrderInfo orderInfo) {
        String depLongitude = orderInfo.getDepLongitude();
        String depLatitude = orderInfo.getDepLatitude();
        String center = depLatitude + "," + depLongitude;

        ArrayList<Integer> radiusList = new ArrayList<>();
        radiusList.add(2000);
        radiusList.add(4000);

        //搜索结果
        ResponseResult<List<TerminalResponse>> result = null;
        for (int i = 0; i < radiusList.size(); i++) {
            Integer radius = radiusList.get(i);
            result = serviceMapClient.aroundSearch(center, radius);
            System.out.println("在半径为" + radius + "的范围内，寻找车辆，结果为：" + JSONArray.fromObject(result.getData()).toString());

            // 获得终端

            // 解析终端
            List<TerminalResponse> data = result.getData();
            for (int j = 0; j < data.size(); j++) {
                TerminalResponse terminalResponse = data.get(j);
                Long carId = terminalResponse.getCarId();

                String longitude = terminalResponse.getLongitude();
                String latitude = terminalResponse.getLatitude();

                // 查询是否有可派单的司机
                ResponseResult<OrderDriverResponse> availableDriver = serviceDriverUserClient.getAvailableDriver(carId);
                if (availableDriver.getCode() == CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode()) {
                    continue;
                } else {
                    System.out.println("有可派单的司机，司机ID为：" + carId);
                    OrderDriverResponse availableDriverData = availableDriver.getData();
                    Long driverId = availableDriverData.getDriverId();
                    String driverPhone = availableDriverData.getDriverPhone();
                    String licenseId = availableDriverData.getLicenseId();
                    String vehicleNo = availableDriverData.getVehicleNo();

                    // 司机有正在进行的订单不允许下单
                    if (IsDriverOrderGoingOn(driverId) > 0) {
                        continue;
                    }

                    QueryWrapper<Car> carQueryWrapper = new QueryWrapper<>();
                    carQueryWrapper.eq("id", carId);

                    //查询当前司机信息
                    orderInfo.setDriverId(driverId);
                    orderInfo.setDriverPhone(driverPhone);
                    orderInfo.setCarId(carId);

                    // 从地图中获取信息
                    orderInfo.setReceiveOrderCarLongitude(longitude);
                    orderInfo.setReceiveOrderCarLatitude(latitude);

                    orderInfo.setReceiveOrderTime(LocalDateTime.now());
                    orderInfo.setLicenseId(licenseId);
                    orderInfo.setVehicleNo(vehicleNo);
                    orderInfo.setOrderStatus(OrderConstants.DRIVER_RECEIVE_ORDER);

                    orderInfoMapper.updateById(orderInfo);

                    break;
                }
            }

            // 根据解析结果出来的终端，查询车辆信息

            // 找到符合的车辆，进行派单

            // 如果派单成功，则退出循环
        }
    }

    //判断下单的城市和计价规则是否存在，即不提供叫车服务
    public boolean ifExist(OrderRequest orderRequest) {
        String fareType = orderRequest.getFareType();
        // 数据库中fareType的值：10000$1
        // index为$的位置的下标
        int index = fareType.indexOf("$");
        // 从0-index的值是cityCode
        String cityCode = fareType.substring(0, index);
        // 从index+1到最后的值是vehicleType
        String vehicleType = fareType.substring(index + 1);

        PriceRule priceRule = new PriceRule();
        priceRule.setCityCode(cityCode);
        priceRule.setVehicleType(vehicleType);

        ResponseResult<Boolean> booleanResponseResult = servicePriceClient.ifExist(priceRule);
        return booleanResponseResult.getData();
    }

    // 判断当前设备是否是黑名单设备
    private boolean isBlackDevice(OrderRequest orderRequest) {
        String deviceCode = orderRequest.getDeviceCode();
        // 生成key
        String deviceCodeKey = RedisPrefixUtils.BLACKDEVICECODEPREFIX + deviceCode;
        // 判断原来是否存在key
        Boolean hasKey = stringRedisTemplate.hasKey(deviceCodeKey);
        if (hasKey) {
            String result = stringRedisTemplate.opsForValue().get(deviceCodeKey);
            int parseInt = Integer.parseInt(result);
            if (parseInt >= 2) {
                //当前设备超过下单次数
                return true;
            } else {
                stringRedisTemplate.opsForValue().increment(deviceCodeKey);
            }
        } else {
            stringRedisTemplate.opsForValue().setIfAbsent(deviceCodeKey, "1", 1L, TimeUnit.HOURS);
        }
        return false;
    }

    //判断乘客是否有正在进行的订单
    public int IsPassengerOrderGoingOn(Long passengerId) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passenger_id", passengerId);
        queryWrapper.and(wrapper -> wrapper.eq("order_status", OrderConstants.ORDER_START)
                .or().eq("order_status", OrderConstants.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status", OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status", OrderConstants.PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstants.PASSENGER_GETOFF)
                .or().eq("order_status", OrderConstants.TO_START_PAY)
        );
        //存在以上任何一种情况都不允许下单
        Integer validOrderNumber = orderInfoMapper.selectCount(queryWrapper);
        return validOrderNumber;
    }

    // 判断司机是否有正在进行的订单
    public int IsDriverOrderGoingOn(Long driverId) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverId);
        queryWrapper.and(wrapper -> wrapper
                .eq("order_status", OrderConstants.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status", OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status", OrderConstants.PICK_UP_PASSENGER)

        );

        Integer validOrderNumber = orderInfoMapper.selectCount(queryWrapper);
        log.info("司机Id：" + driverId + ",正在进行的订单的数量：" + validOrderNumber);

        return validOrderNumber;

    }

}
