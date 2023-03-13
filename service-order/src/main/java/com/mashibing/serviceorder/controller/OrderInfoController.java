package com.mashibing.serviceorder.controller;


import com.mashibing.internalcommon.dto.OrderInfo;
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

}
