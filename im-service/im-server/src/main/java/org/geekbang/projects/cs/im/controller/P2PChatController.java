package org.geekbang.projects.cs.im.controller;

import io.netty.channel.Channel;
import org.geekbang.projects.cs.im.dto.ChatResponse;
import org.geekbang.projects.cs.im.dto.P2PChatRequest;
import org.geekbang.projects.cs.im.entity.ImMessage;
import org.geekbang.projects.cs.im.packet.MessageResponsePacket;
import org.geekbang.projects.cs.im.service.ImMessageService;
import org.geekbang.projects.cs.im.util.ChannelUtil;
import org.geekbang.projects.cs.im.util.Session;
import org.geekbang.projects.cs.im.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/p2p")
public class P2PChatController {

    @Autowired
    @Qualifier("event_delay")
    private ImMessageService imMessageService;

    @PostMapping(value = "/chat")
    public ChatResponse p2pChat(@RequestBody P2PChatRequest request) {
        ChatResponse response = new ChatResponse();
        String userId = request.getToUserId();
        Channel channel = SessionUtil.getChannelByUserId(userId);
        if (channel == null) {
            response.setCode("4001");
            response.setMsg(userId + "没有登录，无法向其发送即时消息！");
            return response;
        }

        Session session = SessionUtil.getSessionByChannel(channel);
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setFromUserName(session.getUserName());
        responsePacket.setFromUserId(request.getFromUserId());
        responsePacket.setMessage(request.getMsg());

        //向客户端写入数据
        ChannelUtil.writeAndFlush(channel,responsePacket);

        //在业务层执行数据持久化操作
        saveImMessage(request);

        return response;
    }

    private void saveImMessage(P2PChatRequest request) {
        ImMessage imMessage = new ImMessage();
        imMessage.setFromUserId(request.getFromUserId())
                .setFromUsername(request.getFromUserId())
                .setToUserId(request.getToUserId())
                .setToUsername(request.getToUserId())
                .setMessage(request.getMsg());

        imMessageService.saveImMessage(imMessage);
    }
}
