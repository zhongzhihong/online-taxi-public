package com.mashibing.servicessepush.controller;

import com.mashibing.internalcommon.util.SsePrefixUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SSEController {

    public static Map<String, SseEmitter> map = new HashMap<>();

    //建立连接
    @GetMapping("/connect")
    public SseEmitter connect(@RequestParam Long userId, @RequestParam String identity) {
        System.out.println("userId=" + userId + ",identity=" + identity);
        SseEmitter sseEmitter = new SseEmitter(0l);

        String sseKey = SsePrefixUtils.generatorSseKey(userId, identity);
        map.put(sseKey, sseEmitter);

        return sseEmitter;
    }

    //推送消息
    @GetMapping("/push")
    public String push(@RequestParam Long userId, @RequestParam String identity, @RequestParam String content) {
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
