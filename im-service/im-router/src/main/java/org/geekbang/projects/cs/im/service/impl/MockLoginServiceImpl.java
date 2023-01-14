package org.geekbang.projects.cs.im.service.impl;

import org.geekbang.projects.cs.im.dto.IMLoginRequest;
import org.geekbang.projects.cs.im.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MockLoginServiceImpl implements LoginService {

    private static Map<String, IMLoginRequest> loginRequestMap = new ConcurrentHashMap<>();

    @Override
    public void login(IMLoginRequest IMLoginRequest) {
        loginRequestMap.putIfAbsent(IMLoginRequest.getUserid(), IMLoginRequest);
    }

    @Override
    public void logout(String userid) {
        loginRequestMap.remove(userid);
    }

    @Override
    public Boolean isLogin(String userid) {
        return loginRequestMap.get(userid) != null;
    }

    @Override
    public IMLoginRequest getLoginInfo(String userid) {
        return loginRequestMap.get(userid);
    }

    @Override
    public Map<String, IMLoginRequest> getAllLoginUser() {
        return loginRequestMap;
    }
}
