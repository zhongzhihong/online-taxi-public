package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.service.ServiceFromMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class ServiceFromMapController {

    @Autowired
    ServiceFromMapService serviceFromMapService;

    @PostMapping("/add")
    public ResponseResult add(String name) {
        return serviceFromMapService.add(name);
    }

}
