package com.mashibing.servicemap.feign;

import com.mashibing.internalcommon.constant.AMapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PointDTO;
import com.mashibing.internalcommon.request.PointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class PointClient {

    @Autowired
    RestTemplate restTemplate;

    @Value("${URL.key}")
    private String URLKey;

    @Value("${URL.sid}")
    private String sid;

    public ResponseResult upload(PointRequest pointRequest) {

        // 拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(AMapConfigConstants.POINT_UPLOAD);
        url.append("?key=" + URLKey);
        url.append("&sid=" + sid);
        url.append("&tid=" + pointRequest.getTid());
        url.append("&trid=" + pointRequest.getTrid());
        url.append("&points=");
        PointDTO[] points = pointRequest.getPoints();
        url.append("%5B");
        for (PointDTO p : points) {
            url.append("%7B");

            String locatetime = p.getLocatetime();
            String location = p.getLocation();

            url.append("%22location%22");
            url.append("%3A");
            url.append("%22" + location + "%22");
            url.append("%2C");
            url.append("%22locatetime%22");
            url.append("%3A");
            url.append(locatetime);
            url.append("%7D");
        }
        url.append("%5D");

        System.out.println("上传位置请求：" + url);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(URI.create(url.toString()), null, String.class);
        System.out.println("上传位置响应：" + stringResponseEntity.getBody());

        return ResponseResult.success();
    }
}
