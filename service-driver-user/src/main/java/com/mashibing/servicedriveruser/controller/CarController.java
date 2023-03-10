package com.mashibing.servicedriveruser.controller;


import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 钟志宏
 * @since 2023-03-09
 */
@RestController
public class CarController {

    @Autowired
    CarService carService;

    @PostMapping("/car")
    public ResponseResult addUser(@RequestBody Car car) {
        return carService.addCar(car);
    }

    @GetMapping("/car")
    public ResponseResult<Car> getCarInfoById(Long carId) {
        return carService.getCarInfoById(carId);
    }

}
