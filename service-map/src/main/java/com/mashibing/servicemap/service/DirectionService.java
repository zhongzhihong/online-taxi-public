package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.servicemap.feign.MapDirectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectionService {

    @Autowired
    MapDirectionClient mapDirectionClient;

    public ResponseResult driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

        //调用第三方地图接口
        mapDirectionClient.direction(depLongitude, depLatitude, destLongitude, destLatitude);

        DirectionResponse directionResponse = new DirectionResponse();
        directionResponse.setDistance(123);
        directionResponse.setDuration(11);

        return ResponseResult.success(directionResponse);
    }
}
