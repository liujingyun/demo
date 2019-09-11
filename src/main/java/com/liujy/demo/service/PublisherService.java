package com.liujy.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author jingyun_liu
 * @Date 2019/9/9 17:43
 * @Version V1.0
 **/
@Service
public class PublisherService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public String sendMessage(String name) {
        try {
            redisTemplate.convertAndSend("TOPIC_USERNAME", name);
            return "消息发送成功了";

        } catch (Exception e) {
            e.printStackTrace();
            return "消息发送失败了";
        }
    }
}
