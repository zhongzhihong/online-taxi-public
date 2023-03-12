package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrackResponse;
import com.mashibing.servicedriveruser.feign.ServiceFromMapClient;
import com.mashibing.servicedriveruser.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class CarService {

    @Autowired
    CarMapper carMapper;

    @Autowired
    ServiceFromMapClient serviceFromMapClient;

    public ResponseResult addCar(Car car) {
        LocalDateTime now = LocalDateTime.now();
        car.setGmtCreate(now);
        car.setGmtModified(now);

        //远程调用获得此车辆对应的tid
        ResponseResult<TerminalResponse> responseResult = serviceFromMapClient.add(car.getVehicleNo());
        String tid = responseResult.getData().getTid();
        car.setTid(tid);

        //远程调用获取此车辆的trid
        ResponseResult<TrackResponse> trackResponseResponseResult = serviceFromMapClient.addTrack(tid);
        String trid = trackResponseResponseResult.getData().getTrid();
        String trname = trackResponseResponseResult.getData().getTrname();
        car.setTrid(trid);
        car.setTrname(trname);

        carMapper.insert(car);
        return ResponseResult.success("");
    }

    public ResponseResult<Car> getCarInfoById(Long carId) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("id", carId);
        List<Car> cars = carMapper.selectByMap(map);

        return ResponseResult.success(cars.get(0));
    }
}
