package com.mashibing.servicedriveruser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashibing.internalcommon.dto.DriverUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DriverUserMapper extends BaseMapper<DriverUser> {
    int selectDriverUserCountByCityCode(@Param("cityCode") String cityCode);
}
