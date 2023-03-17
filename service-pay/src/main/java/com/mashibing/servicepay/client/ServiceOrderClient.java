package com.mashibing.servicepay.client;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-order")
public interface ServiceOrderClient {

    // 订单支付完成
    @PostMapping("/order-info/pay")
    ResponseResult pay(@RequestBody OrderRequest orderRequest);

}
