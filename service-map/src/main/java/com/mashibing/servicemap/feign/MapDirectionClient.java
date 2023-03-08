package com.mashibing.servicemap.feign;

import com.mashibing.internalcommon.constant.AMapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
        urlBuilder.append(AMapConfigConstants.DIRECTION_URL);
        urlBuilder.append("?");
        urlBuilder.append("origin=" + depLongitude + "," + depLatitude + "&");
        urlBuilder.append("destination=" + destLongitude + "," + destLatitude + "&");
        urlBuilder.append("output=json" + "&");
        urlBuilder.append("key=" + URLKey);
        log.info("URL=" + urlBuilder);

        // 调用高德接口
        ResponseEntity<String> directionEntity = restTemplate.getForEntity(urlBuilder.toString(), String.class);
        String directionString = directionEntity.getBody();
        log.info("高德地图路径规划的返回信息：" + directionString);

        // 解析接口
        DirectionResponse directionResponse = parseDirectionEntity(directionString);
        return directionResponse;
    }

    //解析接口的方法，供上面进行调用。因为directionString是一串JSON格式的数据，需要解析出其中的distance、duration
    public DirectionResponse parseDirectionEntity(String directionString) {

        DirectionResponse directionResponse = null;

        try {
            //解析最外层
            JSONObject result = JSONObject.fromObject(directionString);
            //判断是否存在status
            if (result.has(AMapConfigConstants.STATUS)) {
                int status = result.getInt(AMapConfigConstants.STATUS);
                if (status == 1) {
                    //route
                    JSONObject routeObject = result.getJSONObject(AMapConfigConstants.ROUTE);
                    //paths
                    JSONArray pathArray = routeObject.getJSONArray(AMapConfigConstants.PATH);
                    JSONObject pathObject = pathArray.getJSONObject(0);

                    directionResponse = new DirectionResponse();
                    //distance
                    if (pathObject.has(AMapConfigConstants.DISTANCE)) {
                        int distance = pathObject.getInt(AMapConfigConstants.DISTANCE);
                        directionResponse.setDistance(distance);
                    }
                    //duration
                    if (pathObject.has(AMapConfigConstants.DURATION)) {
                        int duration = pathObject.getInt(AMapConfigConstants.DURATION);
                        directionResponse.setDuration(duration);
                    }
                }
            }
        } catch (Exception e) {

        }
        return directionResponse;
    }

}
