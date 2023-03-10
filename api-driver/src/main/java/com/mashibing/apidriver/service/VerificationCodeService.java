package com.mashibing.apidriver.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    public ResponseResult checkAndSendVerificationCode(String driverPhone) {

        // 调用service-driver-user服务，查询改手机号的司机是否存在

        // 获取验证码

        // 发送验证码

        // 存入Redis


        return ResponseResult.success("");
    }

}
