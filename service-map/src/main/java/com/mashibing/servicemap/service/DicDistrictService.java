package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.constant.AMapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DicDistrictService {

    @Value("${URL.key}")
    private String URLKey;

    public ResponseResult initDicDistrict(String keywords) {

        // 拼装请求的URL
        StringBuilder url = new StringBuilder();
        url.append(AMapConfigConstants.DISTRICT_URL);
        url.append("?keywords=" + keywords);
        //这里设置显示下级行政区级数为3，即最大
        url.append("&subdistrict=3");
        url.append("&key=" + URLKey);
        System.out.println("initDicDistrict的结果为：" + url);

        // 解析结果

        // 插入数据库

        return null;
    }
}
