package com.mashibing.servicemap.feign;

import com.mashibing.internalcommon.constant.AMapConfigConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MapDicDistrictClient {

    @Value("${URL.key}")
    private String URLKey;

    @Autowired
    private RestTemplate restTemplate;

    public String dicDistrict(String keywords){

        // 拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(AMapConfigConstants.DISTRICT_URL);
        url.append("?keywords=" + keywords);
        url.append("&subdistrict=3");  //这里设置显示下级行政区级数为3，即最大
        url.append("&key=" + URLKey);
        System.out.println("initDicDistrict的结果为：" + url);

        ResponseEntity<String> forEntity = restTemplate.getForEntity(url.toString(), String.class);
        return forEntity.getBody();
    }

}
