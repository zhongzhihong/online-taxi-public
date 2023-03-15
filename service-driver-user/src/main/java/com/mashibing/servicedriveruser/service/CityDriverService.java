package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityDriverService {

    @Autowired
    DriverUserMapper driverUserMapper;

    // 根据城市编码查询城市是否有可用司机
    public ResponseResult<Boolean> hasAvailableDriver(String cityCode) {
        int result = driverUserMapper.selectDriverUserCountByCityCode(cityCode);
        if (result > 0) {
            return ResponseResult.success(true);
        } else {
            return ResponseResult.success(false);
        }
    }

}
