package org.geekbang.projects.cs.im.service;

import org.geekbang.projects.cs.im.dto.IMLoginRequest;

import java.util.Map;

public interface LoginService {

    /**
     * 用户登录
     */
    void login(IMLoginRequest request);

    /**
     * 用户登出
     */
    void logout(String userid);

    /**
     * 判断用户是否登录
     */
    Boolean isLogin(String userid);

    /**
     * 获取用户登录信息
     */
    IMLoginRequest getLoginInfo(String userid);

    /**
     * 获取所有登录用户
     */
    Map<String, IMLoginRequest> getAllLoginUser();


}
