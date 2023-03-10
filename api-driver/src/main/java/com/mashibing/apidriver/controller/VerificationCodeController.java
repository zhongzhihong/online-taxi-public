package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.VerificationCodeService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class VerificationCodeController {

    @Autowired
    VerificationCodeService verificationCodeService;

    @GetMapping("/verification-code")
    public ResponseResult checkAndSendVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String driverPhone = verificationCodeDTO.getDriverPhone();
        log.info("司机的手机号为：" + driverPhone);
        return verificationCodeService.checkAndSendVerificationCode(driverPhone);
    }

}
