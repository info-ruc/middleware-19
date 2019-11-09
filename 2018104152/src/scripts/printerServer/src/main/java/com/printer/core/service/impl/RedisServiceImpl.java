package com.printer.core.service.impl;

import com.printer.core.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 阻塞地删除列表最左侧元素，如果没有元素则会阻塞直到超时或者发现新元素进来
    // 设置超时时间为30min
    public Object blpop(String key) {
        return redisTemplate.opsForList().leftPop(key, 3, TimeUnit.MINUTES);
    }

    // 在列表右侧加入元素，如果该键不存在，则存为空列表
    public void rpush(String key, Object val) {
        redisTemplate.opsForList().rightPush(key, val);
    }

    public static void main(String[] args) {

    }
}
