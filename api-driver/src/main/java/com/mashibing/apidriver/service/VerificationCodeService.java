package com.mashibing.apidriver.service;

import com.mashibing.apidriver.feign.ServiceUserClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserExistsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VerificationCodeService {

    @Autowired
    ServiceUserClient serviceUserClient;

    public ResponseResult checkAndSendVerificationCode(String driverPhone) {

        // 调用service-driver-user服务，查询改手机号的司机是否存在
        ResponseResult<DriverUserExistsResponse> user = serviceUserClient.getDriverUserByPhone(driverPhone);
        DriverUserExistsResponse data = user.getData();
        int ifExists = data.getIfExists();
        if (ifExists == DriverCarConstants.DRIVER_NOT_EXISTS){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXIST.getCode(),CommonStatusEnum.DRIVER_NOT_EXIST.getValue());
        }
        log.info("手机号为："+driverPhone+"的司机存在");

        // 获取验证码

        // 发送验证码

        // 存入Redis


        return ResponseResult.success("");
    }

}
