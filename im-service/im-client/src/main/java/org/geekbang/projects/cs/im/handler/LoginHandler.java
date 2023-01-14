package org.geekbang.projects.cs.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.geekbang.projects.cs.im.packet.LoginRequestPacket;

public class LoginHandler extends ChannelInboundHandlerAdapter {

    private String userid;
    private String userName;

    public LoginHandler(String userid, String userName) {
        this.userid = userid;
        this.userName = userName;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //构建登录对象，发起登录操作
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(this.userid);
        loginRequestPacket.setUserName(this.userName);

        ctx.channel().writeAndFlush(loginRequestPacket);
    }

}
