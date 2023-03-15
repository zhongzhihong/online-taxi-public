package com.mashibing.servicedriveruser.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.service.CityDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cityDriver")
public class CityDriverController {

    @Autowired
    CityDriverService cityDriverService;

    // 根据城市编码查询城市是否有可用司机
    @GetMapping("/hasAvailableDriver")
    public ResponseResult<Boolean> hasAvailableDriver(String cityCode) {
        return cityDriverService.hasAvailableDriver(cityCode);
    }

}
