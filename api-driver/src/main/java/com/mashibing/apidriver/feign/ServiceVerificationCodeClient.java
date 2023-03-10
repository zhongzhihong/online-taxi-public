package com.mashibing.apidriver.feign;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-verificationCode")
public interface ServiceVerificationCodeClient {

    @GetMapping("/numberCode/{size}")
    ResponseResult<NumberCodeResponse> NumberCode(@PathVariable int size);

}
