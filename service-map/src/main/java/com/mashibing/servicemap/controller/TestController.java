package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.DicDistrict;
import com.mashibing.servicemap.mapper.DicDistrictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    DicDistrictMapper dicDistrictMapper;

    @GetMapping("/test")
    public String test() {
        return "service-map";
    }


}
