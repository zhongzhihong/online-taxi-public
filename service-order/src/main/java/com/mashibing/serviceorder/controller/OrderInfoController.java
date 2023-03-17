package com.mashibing.serviceorder.controller;


import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.serviceorder.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 钟志宏
 * @since 2023-03-13
 */
@RestController
@RequestMapping("/order-info")
public class OrderInfoController {

    @Autowired
    OrderInfoService orderInfoService;

    // 测试代码
    @GetMapping("/add")
    public ResponseResult addInfo() {
        return orderInfoService.addInfo();
    }

    // 插入订单
    @PostMapping("/addOrder")
    public ResponseResult addOrder(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.addOrder(orderRequest);
    }

    // 司机去接乘客
    @PostMapping("/toPickUpPassenger")
    public ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.toPickUpPassenger(orderRequest);
    }

    // 司机到达乘客上车点
    @PostMapping("/arriveDeparture")
    public ResponseResult arriveDeparture(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.arriveDeparture(orderRequest);
    }

    // 司机接到乘客
    @PostMapping("/pickUpPassenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.pickUpPassenger(orderRequest);
    }

    // 乘客到达目的地
    @PostMapping("/passengerGetOff")
    public ResponseResult passengerGetOff(@RequestBody OrderRequest orderRequest){
        return orderInfoService.passengerGetOff(orderRequest);
    }

    // 订单支付完成
    @PostMapping("/pay")
    public ResponseResult pay(@RequestBody OrderRequest orderRequest){
        return orderInfoService.pay(orderRequest);
    }

}
