package org.geekbang.projects.cs.im.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.geekbang.projects.cs.im.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientTest {

    private static Logger logger = LoggerFactory.getLogger(ClientTest.class);
    public static String userid = "101";
    public static String username = "tianyalan";
    public static String host = "127.0.0.1";
    public static int port = 8888;

    public static void main(String[] strings){
        start();
    }

    public static void start() {
        start(userid,username,host,port);
    }

    public static void start(String userid,String username,String host,int port) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new ServerIdleHandler());
                        channel.pipeline().addLast(PacketCodecHandler.getInstance());
                        channel.pipeline().addLast(new ClientIdleHandler());
                        channel.pipeline().addLast(new LoginHandler(userid, username));
                        channel.pipeline().addLast(LoginResponseHandler.getInstance());
                        channel.pipeline().addLast(MessageResponseHandler.getInstance());
                    }
                });
        ChannelFuture future = bootstrap.connect(host,port).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    logger.info("connect to server success!");
                }else{
                    logger.info("failed to connect the server! ");
                    System.exit(0);
                }
            }
        });
        try {
            future.channel().closeFuture().sync();
            logger.info("与服务端断开连接！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
