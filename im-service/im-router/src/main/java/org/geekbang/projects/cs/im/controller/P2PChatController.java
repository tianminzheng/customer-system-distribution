package org.geekbang.projects.cs.im.controller;


import org.geekbang.projects.cs.im.dto.ChatResponse;
import org.geekbang.projects.cs.im.dto.P2PChatRequest;
import org.geekbang.projects.cs.im.service.ChatService;
import org.geekbang.projects.cs.im.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/p2p")
public class P2PChatController {
    private static Logger logger = LoggerFactory.getLogger(P2PChatController.class);

    @Autowired
    @Qualifier("redis")
    private LoginService loginService;

    @Autowired
    private ChatService chatService;

    @PostMapping(value = "/chat")
    public ChatResponse p2pChat(@RequestBody P2PChatRequest request){
        ChatResponse response = new ChatResponse();

        if(!loginService.isLogin(request.getFromUserId())) {
            response.setCode("3002");
            response.setMsg("请先登录");
            return response;
        }

        if(!loginService.isLogin(request.getToUserId())) {
            response.setCode("3003");
            response.setMsg("对方请先登录");
            return response;
        }

        response = chatService.p2pChat(request);

        return response;
    }
}
