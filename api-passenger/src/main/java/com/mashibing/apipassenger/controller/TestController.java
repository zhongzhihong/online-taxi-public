package com.mashibing.apipassenger.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/test")
    public String test() {

        stringRedisTemplate.opsForValue().set("111", "222");
        String s = stringRedisTemplate.opsForValue().get("111");
        System.out.println(s);

        return "test api passenger";
    }

    @GetMapping("/AuthHasToken")
    public ResponseResult AuthHasToken() {
        return ResponseResult.success("AuthHasToken");
    }

    @GetMapping("/AuthNoToken")
    public ResponseResult AuthNoToken() {
        return ResponseResult.success("AuthNoToken");
    }
}
