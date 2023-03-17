package com.mashibing.servicessepush.controller;

import com.mashibing.internalcommon.request.PushRequest;
import com.mashibing.internalcommon.util.SsePrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class SSEController {

    public static Map<String, SseEmitter> map = new HashMap<>();

    //建立连接
    @GetMapping("/connect")
    public SseEmitter connect(@RequestParam Long userId, @RequestParam String identity) {
//        System.out.println("userId=" + userId + ",identity=" + identity);
        SseEmitter sseEmitter = new SseEmitter(0l);

        String sseKey = SsePrefixUtils.generatorSseKey(userId, identity);
        map.put(sseKey, sseEmitter);

        return sseEmitter;
    }

    //推送消息
    @PostMapping("/push")
    public String push(@RequestBody PushRequest pushRequest) {
        Long userId = pushRequest.getUserId();
        String identity = pushRequest.getIdentity();
        String content = pushRequest.getContent();
        log.info("用户ID："+userId+",身份："+identity);
        String sseKey = SsePrefixUtils.generatorSseKey(userId, identity);

        try {
            if (map.containsKey(sseKey)) {
                map.get(sseKey).send(content);
            } else {
                return "推送消息失败";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "给用户" + sseKey + "发送的消息内容为：" + content;
    }

    //关闭连接
    @GetMapping("/close")
    public String close(@RequestParam Long userId, @RequestParam String identity) {
        String sseKey = SsePrefixUtils.generatorSseKey(userId, identity);

        System.out.println("关闭连接" + sseKey);
        if (map.containsKey(sseKey)) {
            map.remove(sseKey);
        }
        return "关闭成功";
    }

}
