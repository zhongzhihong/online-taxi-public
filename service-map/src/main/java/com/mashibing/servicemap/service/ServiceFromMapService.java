package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.feign.ServiceFromMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceFromMapService {

    @Autowired
    ServiceFromMapClient serviceFromMapClient;

    public ResponseResult add(String name){
        return serviceFromMapClient.add(name);
    }

}
