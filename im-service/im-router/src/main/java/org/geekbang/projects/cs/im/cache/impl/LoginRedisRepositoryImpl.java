package org.geekbang.projects.cs.im.cache.impl;

import org.geekbang.projects.cs.im.cache.LoginRedisRepository;
import org.geekbang.projects.cs.im.dto.IMLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class LoginRedisRepositoryImpl implements LoginRedisRepository {

    @Autowired
    private RedisTemplate<String, IMLoginRequest> redisTemplate;

    private static final String HASH_NAME = "ImLogin:";

    @Override
    public void saveLogin(IMLoginRequest imLoginRequest) {
        redisTemplate.opsForValue().set(HASH_NAME + imLoginRequest.getUserid(), imLoginRequest);
    }

    @Override
    public void updateLogin(IMLoginRequest imLoginRequest) {
        redisTemplate.opsForValue().set(HASH_NAME + imLoginRequest.getUserid(), imLoginRequest);
    }

    @Override
    public void deleteLogin(String userId) {
        redisTemplate.delete(HASH_NAME + userId);
    }

    @Override
    public IMLoginRequest findLoginByUserId(String userId) {
        return redisTemplate.opsForValue().get(HASH_NAME + userId);
    }

    @Override
    public Boolean isLogin(String userId) {
        return redisTemplate.hasKey(HASH_NAME + userId);
    }

    @Override
    public List<IMLoginRequest> getAllIMLoginRequests() {
        Set<String> keys = redisTemplate.keys(HASH_NAME + "*");
        List<IMLoginRequest> imLoginRequests = redisTemplate.opsForValue().multiGet(keys);
        return imLoginRequests;
    }
}
