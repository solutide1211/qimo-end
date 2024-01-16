package com.example.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class redisUtil {
    @Autowired
    private RedisTemplate redisTemplate;
    public void set(String key, String value){
        redisTemplate.opsForValue().set(key,value);
    }
//    public Boolean expireAt(String key,Date date){
//        return redisTemplate.expireAt(key,date);
//    }
    public  Boolean expire(String key, long timeout, TimeUnit unit){
        return redisTemplate.expire(key,timeout,unit);
    }
    public String get(String key){
        return key == null ? null : (String) redisTemplate.opsForValue().get(key);
    }
    public void del(String key){
        redisTemplate.delete(key);
    }
}
