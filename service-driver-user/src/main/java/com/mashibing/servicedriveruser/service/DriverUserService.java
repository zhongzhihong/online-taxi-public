package com.mashibing.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.OrderDriverResponse;
import com.mashibing.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import com.mashibing.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class DriverUserService {

    @Autowired
    DriverUserMapper driverUserMapper;

    @Autowired
    DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    @Autowired
    DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    //测试代码
    public ResponseResult getUser() {
        DriverUser driverUser = driverUserMapper.selectById(1);
        return ResponseResult.success(driverUser);
    }

    //添加司机信息
    public ResponseResult addUser(DriverUser driverUser) {

        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);
        driverUserMapper.insert(driverUser);

        //改变司机工作状态
        DriverUserWorkStatus driverUserWorkStatus = new DriverUserWorkStatus();
        driverUserWorkStatus.setDriverId(driverUser.getId());
        driverUserWorkStatus.setWorkStatus(DriverCarConstants.DRIVER_WORK_STATUS_STOP);
        driverUserWorkStatus.setGmtCreate(now);
        driverUserWorkStatus.setGmtModified(now);
        driverUserWorkStatusMapper.insert(driverUserWorkStatus);

        return ResponseResult.success("");
    }

    //修改司机信息
    public ResponseResult updateUser(DriverUser driverUser) {

        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtModified(now);

        driverUserMapper.updateById(driverUser);
        return ResponseResult.success("");
    }

    //根据手机号查询司机是否存在
    public ResponseResult<DriverUser> getDriverUserByPhone(String driverPhone) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("driver_phone", driverPhone);
        map.put("state", DriverCarConstants.DRIVER_STATE_VALID);

        List<DriverUser> driverUsers = driverUserMapper.selectByMap(map);
        if (driverUsers.isEmpty()) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXIST.getCode(), CommonStatusEnum.DRIVER_NOT_EXIST.getValue());
        }
        DriverUser driverUser = driverUsers.get(0);
        return ResponseResult.success(driverUser);
    }

    public ResponseResult<OrderDriverResponse> getAvailableDriver(Long carId) {

        QueryWrapper<DriverCarBindingRelationship> relationshipQueryWrapper = new QueryWrapper<>();
        relationshipQueryWrapper.eq("car_id", carId);
        relationshipQueryWrapper.eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND);
        DriverCarBindingRelationship relationship = driverCarBindingRelationshipMapper.selectOne(relationshipQueryWrapper);
        Long driverId = relationship.getDriverId();

        QueryWrapper<DriverUserWorkStatus> statusQueryWrapper = new QueryWrapper<>();
        statusQueryWrapper.eq("driver_id", driverId);
        statusQueryWrapper.eq("work_status", DriverCarConstants.DRIVER_WORK_STATUS_START);

        DriverUserWorkStatus workStatus = driverUserWorkStatusMapper.selectOne(statusQueryWrapper);
        if (null == workStatus) {
            return ResponseResult.fail(CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode(), CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getValue());
        } else {
            QueryWrapper<DriverUser> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("id", driverId);
            DriverUser driverUser = driverUserMapper.selectOne(userQueryWrapper);

            OrderDriverResponse orderDriverResponse = new OrderDriverResponse();
            orderDriverResponse.setCarId(carId);
            orderDriverResponse.setDriverId(driverId);
            orderDriverResponse.setDriverPhone(driverUser.getDriverPhone());

            return ResponseResult.success(orderDriverResponse);
        }
    }
}
