package com.mashibing.serviceorder.service;

import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderInfoService {

    @Autowired
    OrderInfoMapper orderInfoMapper;

    public ResponseResult addInfo() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setAddress("广东省广州市");
        orderInfoMapper.insert(orderInfo);
        return ResponseResult.success("");
    }

}
