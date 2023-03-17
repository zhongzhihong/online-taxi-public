package com.mashibing.apidriver.service;

import com.mashibing.apidriver.feign.ServerOrderClient;
import com.mashibing.internalcommon.constant.IdentityConstants;
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
    public ResponseResult toPickUpPassenger(OrderRequest orderRequest) {
        return serverOrderClient.toPickUpPassenger(orderRequest);
    }

    // 司机到达乘客上车点
    public ResponseResult arriveDeparture(OrderRequest orderRequest) {
        return serverOrderClient.arriveDeparture(orderRequest);
    }

    // 司机接到乘客
    public ResponseResult pickUpPassenger(OrderRequest orderRequest) {
        return serverOrderClient.pickUpPassenger(orderRequest);
    }

    // 乘客到达目的地
    public ResponseResult passengerGetOff(OrderRequest orderRequest) {
        return serverOrderClient.passengerGetOff(orderRequest);
    }

    // 取消订单
    public ResponseResult cancel(Long orderId) {
        return serverOrderClient.cancel(orderId, IdentityConstants.DRIVER_IDENTITY);
    }

}
