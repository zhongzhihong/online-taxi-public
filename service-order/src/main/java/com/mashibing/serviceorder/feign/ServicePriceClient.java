package com.mashibing.serviceorder.feign;

import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-price")
public interface ServicePriceClient {

    @GetMapping("/price-rule/isLatestPrice")
    ResponseResult<Boolean> isLatestPrice(@RequestParam String fareType, @RequestParam Integer fareVersion);

    @GetMapping("/price-rule/ifExist")
    ResponseResult<Boolean> ifExist(@RequestBody PriceRule priceRule);

    // 计算实际价格
    @PostMapping("/calculate-price")
    ResponseResult<Double> calculatePrice(@RequestParam Integer distance, @RequestParam Integer duration,
                                          @RequestParam String cityCode, @RequestParam String vehicleType);

}
