package com.mashibing.apidriver.service;

import com.mashibing.apidriver.feign.ServerOrderClient;
import com.mashibing.apidriver.feign.ServiceSsePushClient;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.internalcommon.request.PushRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayService {

    @Autowired
    ServiceSsePushClient serviceSsePushClient;

    @Autowired
    ServerOrderClient serverOrderClient;

    public ResponseResult pushPayInfo(Long orderId, String price, Long passengerId) {

        // 封装消息
        JSONObject message = new JSONObject();
        message.put("orderId", orderId);
        message.put("price", price);

        //修改订单状态
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderId(orderId);
        serverOrderClient.pushPayInfo(orderRequest);

        PushRequest pushRequest = new PushRequest();
        pushRequest.setContent(message.toString());
        pushRequest.setUserId(passengerId);
        pushRequest.setIdentity(IdentityConstants.PASSENGER_IDENTITY);
        // 推送消息
        serviceSsePushClient.push(pushRequest);

        return ResponseResult.success("");
    }

}
