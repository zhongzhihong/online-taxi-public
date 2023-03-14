package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.OrderService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-info")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/addOrder")
    public ResponseResult add(@RequestBody OrderRequest orderRequest) {
        System.out.println(orderRequest);
        return orderService.add(orderRequest);
    }

}