package com.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.food.request.LocationUpdate;

@Service
public class RealtimeUpdateHandler {

    private final SimpMessagingTemplate messagingTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RealtimeUpdateHandler(SimpMessagingTemplate messagingTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.redisTemplate = redisTemplate;
    }

    public void publishLocation(LocationUpdate update) {
        String key = "agent:loc:" + update.getAgentId();
        redisTemplate.opsForValue().set(key, update);
        messagingTemplate.convertAndSend("/topic/locations/" + update.getAgentId(), update);
    }
}
