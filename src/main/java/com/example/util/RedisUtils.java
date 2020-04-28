package com.example.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisUtils {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    public Object get(String key){
        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    public boolean set(String key,Object value){
        try{
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            log.error("redis set value exception:{}",e);
            return false;
        }
    }

    public boolean setex(String key,Object value,long expire){
        try {
            redisTemplate.opsForValue().set(key,value,expire, TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean expire(String key,long expire){
        try {
            redisTemplate.expire(key,expire,TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            log.error("redis set key expire exception:{}",e);
            return false;
        }
    }

    public long ttl(String key){
        return redisTemplate.getExpire(key);
    }

    public void del(String ...keys){
        if(keys!=null&&keys.length>0){
            redisTemplate.delete(CollectionUtils.arrayToList(keys));
        }
    }

    public long incrBy(String key,long step){
        return redisTemplate.opsForValue().increment(key,step);
    }

    public boolean setnxAndExpire(String key,Object value,long expire){
        return redisTemplate.opsForValue().setIfAbsent(key,value,expire,TimeUnit.SECONDS);
    }

    public Object getAndSet(String key,Object value){
        return redisTemplate.opsForValue().getAndSet(key,value);
    }

    /**
     *
     * @param key
     * @return 判断key是否存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

}

