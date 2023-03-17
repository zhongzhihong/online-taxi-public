package com.mashibing.apidriver.service;

import com.mashibing.apidriver.feign.ServiceSsePushClient;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayService {

    @Autowired
    ServiceSsePushClient serviceSsePushClient;

    public ResponseResult pushPayInfo(String orderId, String price, Long passengerId) {

        // 封装消息
        JSONObject message = new JSONObject();
        message.put("orderId", orderId);
        message.put("price", price);

        // 推送消息
        serviceSsePushClient.push(passengerId, IdentityConstants.PASSENGER_IDENTITY, message.toString());

        return ResponseResult.success("");
    }

}
