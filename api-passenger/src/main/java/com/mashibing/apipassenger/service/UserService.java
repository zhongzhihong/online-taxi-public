package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.feign.ServicePassengerUserClient;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    ServicePassengerUserClient servicePassengerUserClient;

    public ResponseResult getUserByAccessToken(String accessToken) {

        log.info("accessToken:" + accessToken);

        //解析accessToken，拿到手机号
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        log.info("phone:" + phone);

        //根据手机号查询用户信息
        ResponseResult userByPhone = servicePassengerUserClient.getUserByPhone(phone);
        return ResponseResult.success(userByPhone.getData());
    }
}