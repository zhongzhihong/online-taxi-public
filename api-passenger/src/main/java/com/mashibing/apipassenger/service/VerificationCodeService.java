package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.feign.ServicePassengerUserClient;
import com.mashibing.apipassenger.feign.ServiceVerificationCodeClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import com.mashibing.internalcommon.response.TokenResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    @Autowired
    ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ServicePassengerUserClient servicePassengerUserClient;

    public static String VERIFICATIONCODEPREFIX = "VerificationCode-";

    public ResponseResult getCode(String passengerPhone) {
        // 调用验证码服务，获取验证码
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        // 存入Redis
        String key = GeneratorKeyByPassengerPhone(passengerPhone);
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);

        // 返回信息
        return ResponseResult.success();
    }

    // 根据手机号，生成key
    public String GeneratorKeyByPassengerPhone(String passengerPhone) {
        return VERIFICATIONCODEPREFIX + passengerPhone;
    }

    public ResponseResult checkCode(String passengerPhone, String verificationCode) {
        // 根据手机号，去Redis中读取验证码
        String key = GeneratorKeyByPassengerPhone(passengerPhone);
        String redisCode = stringRedisTemplate.opsForValue().get(key);
        System.out.println("Redis中的验证码：" + redisCode);

        // 校验验证码
        if (StringUtils.isBlank(redisCode)) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode()).setMessage(CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        //trim():去除空格函数
        if (!verificationCode.trim().equals(redisCode.trim())) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode()).setMessage(CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }

        // 判断原来是否有用户，并进行对应的处理
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        servicePassengerUserClient.LoginOrRegister(verificationCodeDTO);

        // 颁发令牌
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("token Response");

        return ResponseResult.success(tokenResponse);
    }
}
