package com.mashibing.apipassenger.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    public String getCode(String passengerPhone) {

        // 调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");
        String code = "1234";

        // 存入Redis
        System.out.println("存入Redis");

        // 返回信息
        JSONObject result = new JSONObject();
        result.put("code", 200);
        result.put("message", "success");
        return result.toString();
    }
}
