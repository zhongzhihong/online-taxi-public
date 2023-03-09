package com.mashibing.apiboss.controller;

import com.mashibing.apiboss.service.DriverUserService;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverUserController {

    @Autowired
    DriverUserService driverUserService;

    @PostMapping("/driver-user")
    public ResponseResult addUser(@RequestBody DriverUser driverUser) {
        return driverUserService.addUser(driverUser);
    }

}
