package org.geekbang.projects.cs.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerIdleHandler extends IdleStateHandler {

    private static int HEAT_BEAT_TIME = 150;

    private static Logger logger = LoggerFactory.getLogger(ServerIdleHandler.class);

    public ServerIdleHandler() {
        super(0, 0, HEAT_BEAT_TIME);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        logger.warn(HEAT_BEAT_TIME + "内没有收到心跳，关闭连接");

        ctx.channel().close();
    }
}
