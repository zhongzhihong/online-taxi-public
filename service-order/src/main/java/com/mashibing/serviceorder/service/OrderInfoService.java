package com.mashibing.serviceorder.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.OrderConstants;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.serviceorder.feign.ServicePriceClient;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderInfoService {

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    ServicePriceClient servicePriceClient;

    // 测试代码
    public ResponseResult addInfo() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setAddress("广东省广州市");
        orderInfoMapper.insert(orderInfo);
        return ResponseResult.success("");
    }

    // 插入订单
    public ResponseResult addOrder(OrderRequest orderRequest) {

        //需要判断计价规则的版本是否为最新的
        ResponseResult<Boolean> latestPrice = servicePriceClient.isLatestPrice(orderRequest.getFareType(), orderRequest.getFareVersion());
        if (!(latestPrice.getData())) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode(), CommonStatusEnum.PRICE_RULE_CHANGED.getValue());
        }

        //需要插入的信息
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest, orderInfo);
        orderInfo.setOrderStatus(OrderConstants.ORDER_START);
        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);

        orderInfoMapper.insert(orderInfo);

        return ResponseResult.success("");
    }

}
