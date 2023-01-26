package org.geekbang.projects.cs.im.service.impl;

import org.geekbang.projects.cs.im.cache.LoginRedisRepository;
import org.geekbang.projects.cs.im.dto.IMLoginRequest;
import org.geekbang.projects.cs.im.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service("redis")
@Primary
public class RedisLoginServiceImpl implements LoginService {

    @Autowired
    private LoginRedisRepository loginRedisRepository;

    @Override
    public void login(IMLoginRequest IMLoginRequest) {
        loginRedisRepository.saveLogin(IMLoginRequest);
    }

    @Override
    public void logout(String userid) {
        loginRedisRepository.deleteLogin(userid);
    }

    @Override
    public Boolean isLogin(String userid) {
//        return Objects.nonNull(loginRedisRepository.findLoginByUserId(userid));

        return loginRedisRepository.isLogin(userid);
    }

    @Override
    public IMLoginRequest getLoginInfo(String userid) {
        return loginRedisRepository.findLoginByUserId(userid);
    }

    @Override
    public Map<String, IMLoginRequest> getAllLoginUser() {
        List<IMLoginRequest> imLoginRequests = loginRedisRepository.getAllIMLoginRequests();
        Map<String, IMLoginRequest> map = new HashMap<>();
        for(IMLoginRequest imLoginRequest : imLoginRequests) {
            map.put(imLoginRequest.getUserid(), imLoginRequest);
        }

        return map;
    }
}
