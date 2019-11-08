package com.printer.core.service;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public interface RedisService {
    // 阻塞地删除列表最左侧元素，如果没有元素则会阻塞直到超时或者发现新元素进来
    public Object blpop(String key);

    // 在列表右侧加入元素
    public void rpush(String key, Object val);
}
