package com.mashibing.serviceorder.feign;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrsearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("service-map")
public interface ServiceMapClient {

    @PostMapping("/terminal/aroundsearch")
    ResponseResult<List<TerminalResponse>> aroundSearch(@RequestParam String center, @RequestParam Integer radius);

    @PostMapping("/terminal/trsearch")
    ResponseResult<TrsearchResponse> trSearch(@RequestParam String tid, @RequestParam Long starttime, @RequestParam Long endtime);

}
