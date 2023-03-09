package com.mashibing.apiboss.service;

import com.mashibing.apiboss.client.ServiceDriverUserClient;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DriverUserService {

    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult addUser(DriverUser driverUser) {
        serviceDriverUserClient.addUser(driverUser);
        return ResponseResult.success("");
    }

    public ResponseResult updateUser(DriverUser driverUser) {
        serviceDriverUserClient.updateUser(driverUser);
        return ResponseResult.success("");
    }

}
