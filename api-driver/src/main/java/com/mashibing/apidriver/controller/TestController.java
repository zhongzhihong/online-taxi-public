package com.mashibing.apidriver.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "api-driver";
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
