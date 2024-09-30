package com.abdelrahman.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;


    public boolean addOrderLock(String idempotentKey,Long ttl){
        Boolean status = redisTemplate.opsForValue().setIfAbsent(idempotentKey,"true",ttl, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(status);
    }

    public boolean existsIdempotentKey(String idempotentKey){
        return  redisTemplate.hasKey(idempotentKey);
    }

    public boolean removeIdempotentKey(String idempotentKey){
        return redisTemplate.delete(idempotentKey);
    }
}
