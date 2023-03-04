package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.feign.ServiceVerificationCodeClient;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    @Autowired
    ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    public ResponseResult getCode(String passengerPhone) {

        // 调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        System.out.println("api-passenger服务收到的验证码为：" + numberCode);

        // 存入Redis
        System.out.println("存入Redis");
        String VERIFICATIONCODEPREFIX = "VerificationCode-";
        String key = VERIFICATIONCODEPREFIX + passengerPhone;
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);

        // 返回信息
        return ResponseResult.success();
    }
}
