package com.mashibing.servicedriveruser.feign;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrackResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-map")
public interface ServiceFromMapClient {

    @PostMapping("/terminal/add")
    ResponseResult<TerminalResponse> add(@RequestParam String name,@RequestParam String desc);

    @PostMapping("/track/add")
    ResponseResult<TrackResponse> addTrack(@RequestParam String tid);

}
