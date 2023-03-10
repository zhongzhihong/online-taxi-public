package com.mashibing.servicedriveruser.controller;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserExistsResponse;
import com.mashibing.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    DriverUserService driverUserService;

    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody DriverUser driverUser) {
        return driverUserService.addUser(driverUser);
    }

    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser) {
        return driverUserService.updateUser(driverUser);
    }

    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult getDriverUserByPhone(@PathVariable String driverPhone) {

        ResponseResult<DriverUser> driverUserByPhone = driverUserService.getDriverUserByPhone(driverPhone);
        DriverUser DriverUserDB = driverUserByPhone.getData();
        DriverUserExistsResponse response = new DriverUserExistsResponse();
        int IfExists = 1;
        if (DriverUserDB == null) {
            IfExists = 0;
            response.setDriverPhone(driverPhone);
            response.setIfExists(IfExists);
        } else {
            response.setDriverPhone(DriverUserDB.getDriverPhone());
            response.setIfExists(IfExists);
        }

        return ResponseResult.success(response);
    }

}
