package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.servicemap.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/direction")
public class DirectionController {

    @Autowired
    DirectionService directionService;

    //根据起点经纬度和终点经纬度获取距离和时长
    @GetMapping("/driving")
    public ResponseResult driving(@RequestBody ForecastPriceDTO forecastPriceDTO) {

        String depLongitude = forecastPriceDTO.getDepLongitude();
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();

        return directionService.driving(depLongitude, depLatitude, destLongitude, destLatitude);
    }

}
