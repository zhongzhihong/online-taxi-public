package com.mashibing.servicemap.feign;

import com.mashibing.internalcommon.constant.URLPrefixConstants;
import com.mashibing.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class MapDirectionClient {

    @Autowired
    RestTemplate restTemplate;

    @Value("${URL.key}")
    private String URLKey;

    public DirectionResponse direction(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

        // 组装请求调用URL
        StrBuilder urlBuilder = new StrBuilder();
        urlBuilder.append(URLPrefixConstants.DIRECTION_URL);
        urlBuilder.append("?");
        urlBuilder.append("origin=" + depLongitude + "," + depLatitude + "&");
        urlBuilder.append("destination=" + destLongitude + "," + destLatitude + "&");
        urlBuilder.append("output=json" + "&");
        urlBuilder.append("key=" + URLKey);
        log.info("URL=" + urlBuilder);

        // 调用高德接口
        ResponseEntity<String> forEntity = restTemplate.getForEntity(urlBuilder.toString(), String.class);
        log.info("高德地图路径规划的返回信息：" + forEntity.getBody());

        // 解析接口

        return null;
    }

}
