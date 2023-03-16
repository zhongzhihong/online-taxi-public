package com.mashibing.servicessepush.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SSEController {

    public static Map<String, SseEmitter> map = new HashMap<>();

    @GetMapping("/connect/{driverId}")
    public SseEmitter connect(@PathVariable String driverId) {
        System.out.println("司机ID=" + driverId);
        SseEmitter sseEmitter = new SseEmitter(0l);

        map.put(driverId, sseEmitter);

        return sseEmitter;
    }

    @GetMapping("/push")
    public String push(@RequestParam String driverId, @RequestParam String content) {
        try {
            if (map.containsKey(driverId)) {
                map.get(driverId).send(content);
            } else {
                return "推送消息失败";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "给用户" + driverId + "发送的消息内容为：" + content;
    }

    @GetMapping("/close/{driverId}")
    public String close(@PathVariable String driverId) {
        System.out.println("关闭连接" + driverId);
        if (map.containsKey(driverId)) {
            map.remove(driverId);
        }
        return "关闭成功";
    }

}
