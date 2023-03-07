package com.mashibing.apipassenger.feign;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {

    @PostMapping("/user")
    ResponseResult LoginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO);

    @GetMapping("/user/{passengerPhone}")
    ResponseResult getUserByPhone(@PathVariable String passengerPhone);
}
