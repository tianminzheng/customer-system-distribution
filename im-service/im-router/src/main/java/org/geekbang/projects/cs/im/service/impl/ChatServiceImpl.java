package org.geekbang.projects.cs.im.service.impl;

import org.geekbang.projects.cs.im.dto.ChatResponse;
import org.geekbang.projects.cs.im.dto.IMLoginRequest;
import org.geekbang.projects.cs.im.dto.P2PChatRequest;
import org.geekbang.projects.cs.im.service.ChatService;
import org.geekbang.projects.cs.im.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private LoginService loginService;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public ChatResponse p2pChat(P2PChatRequest request) {

        //先通过登录信息获取模板服务器地址
        IMLoginRequest loginRequest = loginService.getLoginInfo(request.getToUserId());

        //向目标服务器地址发起聊天请求
        ChatResponse response = restTemplate.postForObject("http://" + loginRequest.getServerHost() + ":" + loginRequest.getHttpPort() + "/p2p/chat",
                request, ChatResponse.class);

        return response;
    }
}
