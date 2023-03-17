package com.mashibing.apidriver.service;

import com.mashibing.apidriver.feign.ServiceUserClient;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    ServiceUserClient serviceUserClient;

    public ResponseResult updateUser(DriverUser driverUser) {
        serviceUserClient.updateUser(driverUser);
        return ResponseResult.success("");
    }

    public ResponseResult changeUserStatus(DriverUserWorkStatus driverUserWorkStatus) {
        serviceUserClient.changeDriverUserStatus(driverUserWorkStatus);
        return ResponseResult.success("");
    }

}
