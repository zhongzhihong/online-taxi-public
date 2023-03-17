package com.mashibing.apidriver.feign;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-order")
@RequestMapping("/order-info")
public interface ServerOrderClient {

    // 司机去接乘客
    @PostMapping("/toPickUpPassenger")
    ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest);

    // 司机到达乘客上车点
    @PostMapping("/arriveDeparture")
    ResponseResult arriveDeparture(@RequestBody OrderRequest orderRequest);

    // 司机接到乘客
    @PostMapping("/pickUpPassenger")
    ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest);

    // 乘客到达目的地
    @PostMapping("/passengerGetOff")
    ResponseResult passengerGetOff(@RequestBody OrderRequest orderRequest);

    @PostMapping("/pushPayInfo")
    ResponseResult pushPayInfo(@RequestBody OrderRequest orderRequest);

    // 取消订单
    @PostMapping("/cancel")
    ResponseResult cancel(@RequestParam Long orderId, @RequestParam String identity);

}
