package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.feign.MapDicDistrictClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DicDistrictService {

    @Autowired
    MapDicDistrictClient mapDicDistrictClient;

    public ResponseResult initDicDistrict(String keywords) {

        // 请求地图
        String dicDistrict = mapDicDistrictClient.dicDistrict(keywords);
        System.out.println("请求地图：" + dicDistrict);

        // 解析结果

        // 插入数据库

        return null;
    }
}
