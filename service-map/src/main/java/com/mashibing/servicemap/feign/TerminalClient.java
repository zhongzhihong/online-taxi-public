package com.mashibing.servicemap.feign;

import com.mashibing.internalcommon.constant.AMapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrsearchResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class TerminalClient {

    @Autowired
    RestTemplate restTemplate;

    @Value("${URL.key}")
    private String URLKey;

    @Value("${URL.sid}")
    private String sid;

    public ResponseResult<TerminalResponse> add(String name, String desc) {
        // 拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(AMapConfigConstants.TERMINAL_ADD);
        url.append("?key=" + URLKey);
        url.append("&sid=" + sid);
        url.append("&name=" + name);
        //创建终端时加入了desc（车辆信息ID）
        url.append("&desc=" + desc);
        System.out.println("创建终端的URL为：" + url);

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url.toString(), null, String.class);
        Object body = stringResponseEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        String tid = data.getString("tid");
        TerminalResponse terminalResponse = new TerminalResponse();
        terminalResponse.setTid(tid);
        return ResponseResult.success(terminalResponse);
    }

    public ResponseResult<List<TerminalResponse>> aroundsearch(String center, Integer radius) {
        StringBuilder url = new StringBuilder();
        url.append(AMapConfigConstants.TERMINAL_AROUNDSEARCH);
        url.append("?key=" + URLKey);
        url.append("&sid=" + sid);
        url.append("&center=" + center);
        url.append("&radius=" + radius);

        System.out.println("终端搜索请求：" + url);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url.toString(), null, String.class);
        System.out.println("终端搜索响应：" + stringResponseEntity.getBody());

        // 解析终端搜索结果
        String body = stringResponseEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");

        List<TerminalResponse> terminalResponseList = new ArrayList<>();
        JSONArray results = data.getJSONArray("results");

        for (int i = 0; i < results.size(); i++) {
            TerminalResponse terminalResponse = new TerminalResponse();

            JSONObject jsonObject = results.getJSONObject(i);
            String desc = jsonObject.getString("desc");
            Long carId = Long.parseLong(desc);
            String tid = jsonObject.getString("tid");

            JSONObject location = jsonObject.getJSONObject("location");
            String longitude = location.getString("longitude");
            String latitude = location.getString("latitude");

            terminalResponse.setCarId(carId);
            terminalResponse.setTid(tid);
            terminalResponse.setLongitude(longitude);
            terminalResponse.setLatitude(latitude);

            terminalResponseList.add(terminalResponse);
        }

        return ResponseResult.success(terminalResponseList);
    }

    public ResponseResult<TrsearchResponse> trSearch(String tid, Long starttime, Long endtime) {

        StringBuilder url = new StringBuilder();
        url.append(AMapConfigConstants.TERMINAL_TRSEARCH);
        url.append("?key=" + URLKey);
        url.append("&sid=" + sid);
        url.append("&tid=" + tid);
        url.append("&starttime=" + starttime);
        url.append("&endtime=" + endtime);

        System.out.println("高德地图查询轨迹结果请求：" + url);
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url.toString(), String.class);
        System.out.println("高德地图查询轨迹结果响应：" + forEntity.getBody());

        JSONObject result = JSONObject.fromObject(forEntity.getBody());
        JSONObject data = result.getJSONObject("data");

        int counts = data.getInt("counts");
        if (counts == 0) {
            return null;
        }

        JSONArray tracks = data.getJSONArray("tracks");
        long driveMile = 0L;
        long driveTime = 0L;
        for (int i = 0; i < tracks.size(); i++) {
            JSONObject jsonObject = tracks.getJSONObject(i);

            long distance = jsonObject.getLong("distance");
            driveMile = driveMile + distance;

            long time = jsonObject.getLong("time");
            time = time / (1000 * 60);
            driveTime = driveTime + time;
        }

        TrsearchResponse trsearchResponse = new TrsearchResponse();
        trsearchResponse.setDriveMile(driveMile);
        trsearchResponse.setDriveTime(driveTime);
        return ResponseResult.success(trsearchResponse);
    }

}
