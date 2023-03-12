package com.mashibing.apidriver.service;

import com.mashibing.apidriver.feign.ServiceMapClient;
import com.mashibing.apidriver.feign.ServiceUserClient;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ApiDriverPointRequest;
import com.mashibing.internalcommon.request.PointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {

    @Autowired
    ServiceUserClient serviceUserClient;

    @Autowired
    ServiceMapClient serviceMapClient;

    public ResponseResult upload(ApiDriverPointRequest apiDriverPointRequest) {

        // 获取carId
        Long carId = apiDriverPointRequest.getCarId();

        // 调用service-driver-user服务，完成通过carId获取tid、trid
        ResponseResult<Car> carInfoById = serviceUserClient.getCarInfoById(carId);
        Car data = carInfoById.getData();
        String tid = data.getTid();
        String trid = data.getTrid();

        // 调用地图去上传
        PointRequest pointRequest = new PointRequest();
        pointRequest.setTid(tid);
        pointRequest.setTrid(trid);
        pointRequest.setPoints(apiDriverPointRequest.getPoints());

        return serviceMapClient.upload(pointRequest);
    }

}
