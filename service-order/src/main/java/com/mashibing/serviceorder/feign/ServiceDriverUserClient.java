package com.mashibing.serviceorder.feign;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    @GetMapping("/cityDriver/hasAvailableDriver")
    ResponseResult<Boolean> hasAvailableDriver(@RequestParam String cityCode);

}
