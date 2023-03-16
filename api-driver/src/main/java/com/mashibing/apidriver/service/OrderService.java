package com.mashibing.apidriver.service;

import com.mashibing.apidriver.feign.ServerOrderClient;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class OrderService {

    @Autowired
    ServerOrderClient serverOrderClient;

    // 司机去接乘客
    @PostMapping("/toPickUpPassenger")
    public ResponseResult toPickUpPassenger(OrderRequest orderRequest) {
        return serverOrderClient.toPickUpPassenger(orderRequest);
    }

    // 司机到达乘客上车点
    @PostMapping("/arriveDeparture")
    public ResponseResult arriveDeparture(OrderRequest orderRequest) {
        return serverOrderClient.arriveDeparture(orderRequest);
    }

    // 司机接到乘客
    @PostMapping("/pickUpPassenger")
    public ResponseResult pickUpPassenger(OrderRequest orderRequest) {
        return serverOrderClient.pickUpPassenger(orderRequest);
    }

    // 乘客到达目的地
    @PostMapping("/passengerGetOff")
    public ResponseResult passengerGetOff(OrderRequest orderRequest) {
        return serverOrderClient.passengerGetOff(orderRequest);
    }

}
