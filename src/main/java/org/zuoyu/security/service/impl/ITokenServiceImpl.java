package org.zuoyu.security.service.impl;

import org.zuoyu.security.service.ITokenService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author : zuoyu
 * @project : proj-supergenius-vc-admin
 * @description :
 * @date : 2019-12-09 15:22
 **/
@Service
public class ITokenServiceImpl implements ITokenService {

    private final RedisTemplate<String, Object> redisTemplate;

    public ITokenServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void depositToken(String key, User user) {
        try {
            redisTemplate.opsForValue().set(key, user, EXPIRE_TIME, TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
