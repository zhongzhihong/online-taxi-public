package com.mashibing.servicedriveruser.controller;

import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserExistsResponse;
import com.mashibing.internalcommon.response.OrderDriverResponse;
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
    public ResponseResult<DriverUserExistsResponse> getDriverUserByPhone(@PathVariable String driverPhone) {

        ResponseResult<DriverUser> driverUserByPhone = driverUserService.getDriverUserByPhone(driverPhone);
        DriverUser DriverUserDB = driverUserByPhone.getData();
        DriverUserExistsResponse response = new DriverUserExistsResponse();
        int IfExists = DriverCarConstants.DRIVER_EXISTS;
        if (DriverUserDB == null) {
            IfExists = DriverCarConstants.DRIVER_NOT_EXISTS;
            response.setDriverPhone(driverPhone);
            response.setIfExists(IfExists);
        } else {
            response.setDriverPhone(DriverUserDB.getDriverPhone());
            response.setIfExists(IfExists);
        }

        return ResponseResult.success(response);
    }

    // 根据车辆ID，查询可以派单的司机的信息
    @GetMapping("/getAvailableDriver/{carId}")
    public ResponseResult<OrderDriverResponse> getAvailableDriver(@PathVariable("carId") Long carId) {
        return driverUserService.getAvailableDriver(carId);
    }

}
