package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.feign.ServiceVerificationCodeClient;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    @Autowired
    ServiceVerificationCodeClient serviceVerificationCodeClient;

    public String getCode(String passengerPhone) {

        // 调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        System.out.println("api-passenger服务收到的验证码为：" + numberCode);

        // 存入Redis
        System.out.println("存入Redis");

        // 返回信息
        JSONObject result = new JSONObject();
        result.put("code", 200);
        result.put("message", "success");
        return result.toString();
    }
}
