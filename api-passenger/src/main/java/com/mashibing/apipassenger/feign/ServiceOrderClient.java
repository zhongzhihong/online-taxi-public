package com.mashibing.apipassenger.feign;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-order")
public interface ServiceOrderClient {

    @PostMapping("/order-info/addOrder")
    ResponseResult add(@RequestBody OrderRequest orderRequest);

    // 订单取消
    @PostMapping("/order-info/cancel")
    ResponseResult cancel(@RequestParam Long orderId,@RequestParam String identity);

}
