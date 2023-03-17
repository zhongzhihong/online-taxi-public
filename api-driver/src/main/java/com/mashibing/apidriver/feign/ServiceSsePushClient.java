package com.mashibing.apidriver.feign;

import com.mashibing.internalcommon.request.PushRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-sse-push")
public interface ServiceSsePushClient {

    //推送消息
    @PostMapping("/push")
    String push(@RequestBody PushRequest pushRequest);

}
