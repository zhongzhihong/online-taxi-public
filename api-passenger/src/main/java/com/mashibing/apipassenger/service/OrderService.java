package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.feign.ServiceOrderClient;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class OrderService {

    @Autowired
    ServiceOrderClient serviceOrderClient;

    public ResponseResult add(@RequestBody OrderRequest orderRequest) {
        return serviceOrderClient.add(orderRequest);
    }

}
