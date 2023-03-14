package com.mashibing.serviceorder.feign;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-price")
public interface ServicePriceClient {

    @GetMapping("/price-rule/isLatestPrice")
    ResponseResult<Boolean> isLatestPrice(@RequestParam String fareType, @RequestParam Integer fareVersion);

}
