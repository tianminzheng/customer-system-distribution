package org.geekbang.projects.cs.im.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.geekbang.projects.cs.im.packet.DefaultErrorPacket;
import org.geekbang.projects.cs.im.packet.LoginRequestPacket;
import org.geekbang.projects.cs.im.packet.MessageRequestPacket;
import org.geekbang.projects.cs.im.packet.MessageResponsePacket;
import org.geekbang.projects.cs.im.util.Session;
import org.geekbang.projects.cs.im.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    private MessageRequestHandler(){}

    private static MessageRequestHandler instance = new MessageRequestHandler();

    public static MessageRequestHandler getInstance(){
        return instance;
    }

    private static Logger logger = LoggerFactory.getLogger(MessageRequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
        logger.info("收到来自客户端消息：{}", messageRequestPacket.getMessage());

        //获取Session，准备发送方
        Session session = SessionUtil.getSessionByChannel(channelHandlerContext.channel());
        String fromUserId = session.getUserId();
        String fromUserName = session.getUserName();

        //构造响应结果
        MessageResponsePacket response = new MessageResponsePacket();
        response.setFromUserId(fromUserId);
        response.setFromUserName(fromUserName);
        response.setMessage(messageRequestPacket.getMessage());

        //发送聊天消息
        p2tChat(channelHandlerContext, messageRequestPacket.getToUserId(), response);
    }

    private void p2tChat(ChannelHandlerContext ctx, String toUserId, MessageResponsePacket response) {
        Channel channel = SessionUtil.getChannelByUserId(toUserId);

        //校验目前channel是否合法，对方用户是否在线
        if(channel != null && SessionUtil.hasLogin(channel)) {
            channel.writeAndFlush(response);
        } else {
            DefaultErrorPacket defaultErrorPacket = new DefaultErrorPacket();
            defaultErrorPacket.setCode("3001");
            defaultErrorPacket.setMsg("该用户没有登录，无法发送消息");
            channel.writeAndFlush(defaultErrorPacket);
        }
    }

}
