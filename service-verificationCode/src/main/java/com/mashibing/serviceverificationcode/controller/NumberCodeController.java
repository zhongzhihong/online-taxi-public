package com.mashibing.serviceverificationcode.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public ResponseResult NumberCode(@PathVariable int size) {

        System.out.println("size:" + size);
        // 生成验证码
        int mathRandom = (int) ((Math.random() * 9 + 1) * (Math.pow(10, size - 1)));
        System.out.println("service-verificationCode服务生成的验证码为：" + mathRandom);

        //返回数据
        NumberCodeResponse response = new NumberCodeResponse();
        response.setNumberCode(mathRandom);

        return ResponseResult.success(response);
    }
}
