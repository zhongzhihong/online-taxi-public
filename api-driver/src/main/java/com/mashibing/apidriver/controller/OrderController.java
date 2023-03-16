package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.OrderService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    // 司机去接乘客
    @PostMapping("/toPickUpPassenger")
    public ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest) {
        return orderService.toPickUpPassenger(orderRequest);
    }

    // 司机到达乘客上车点
    @PostMapping("/arriveDeparture")
    public ResponseResult arriveDeparture(@RequestBody OrderRequest orderRequest) {
        return orderService.arriveDeparture(orderRequest);
    }

    // 司机接到乘客
    @PostMapping("/pickUpPassenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest) {
        return orderService.pickUpPassenger(orderRequest);
    }

    // 乘客到达目的地
    @PostMapping("/passengerGetOff")
    public ResponseResult passengerGetOff(@RequestBody OrderRequest orderRequest) {
        return orderService.passengerGetOff(orderRequest);
    }

}
