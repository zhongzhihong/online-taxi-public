package com.mashibing.servicemap.feign;

import com.mashibing.internalcommon.constant.AMapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TrackResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceTrackClient {

    @Autowired
    RestTemplate restTemplate;

    @Value("${URL.key}")
    private String URLKey;

    @Value("${URL.sid}")
    private String sid;

    public ResponseResult<TrackResponse> add(String tid) {

        // 拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(AMapConfigConstants.TRACK_ADD);
        url.append("?key=" + URLKey);
        url.append("&sid=" + sid);
        url.append("&tid=" + tid);
        System.out.println("创建轨迹的URL为：" + url);

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url.toString(), null, String.class);
        String body = stringResponseEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        String trid = data.getString("trid");
        String trname = "";
        if (data.has("trname")) {
            trname = data.getString("trname");
        }

        TrackResponse trackResponse = new TrackResponse();
        trackResponse.setTrid(trid);
        trackResponse.setTrname(trname);
        return ResponseResult.success(trackResponse);
    }

}
