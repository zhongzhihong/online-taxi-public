package com.mashibing.servicepassengeruser.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user")
    public ResponseResult LoginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("UserController:" + passengerPhone);
        return userService.LoginOrRegister(passengerPhone);
    }

    @GetMapping("/user/{passengerPhone}")
    public ResponseResult getUser(@PathVariable String passengerPhone) {
        return userService.getUserByPhone(passengerPhone);
    }

}
