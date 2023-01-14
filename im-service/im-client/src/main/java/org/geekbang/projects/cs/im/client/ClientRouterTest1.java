package org.geekbang.projects.cs.im.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.geekbang.projects.cs.im.dto.IMLoginRequest;
import org.geekbang.projects.cs.im.dto.IMLoginResponse;
import org.geekbang.projects.cs.im.dto.IMServerInfo;
import org.geekbang.projects.cs.im.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class ClientRouterTest1 {

    private static Logger logger = LoggerFactory.getLogger(ClientRouterTest1.class);
    public static String userid = "101";
    public static String username = "tianyalan";
    public static String routeHost = "127.0.0.1";
    public static int routePort = 9003; //路由服务地址

    public static void main(String[] strings){
        start();
    }

    public static void start() {

        //1. 从Router获取ServerInfo信息
        IMServerInfo serverInfo = getIMServerInfoFromRouter(routeHost, routePort);

        //2. 调用Router执行登录操作
        loginToRoute(serverInfo);

        //3. 与Server建立长连接
        connectToServer(serverInfo);
    }

    private static IMServerInfo getIMServerInfoFromRouter(String routeHost, int routePort) {
        RestTemplate restTemplate = new RestTemplate();
        IMServerInfo serverInfo = restTemplate.getForObject("http://" + routeHost + ":" + routePort + "/serverInfo/", IMServerInfo.class);

        logger.info("获取服务端信息：{}", serverInfo);

        return serverInfo;
    }

    private static void loginToRoute(IMServerInfo serverInfo) {
        RestTemplate template = new RestTemplate();
        IMLoginRequest login = new IMLoginRequest(userid,username,serverInfo.getHost(),serverInfo.getNettyPort(),serverInfo.getHttpPort());
        IMLoginResponse response = template.postForObject("http://" + routeHost + ":" + routePort + "/login/",login,IMLoginResponse.class);
        logger.info("登录返回信息为：{}",response);
        if (response.success()){
            logger.info("登录成功");
        }else{
            logger.info("登录失败：{}，程序即将退出...",response.getMsg());
            System.exit(0);
        }
    }

    private static void connectToServer(IMServerInfo serverInfo) {
        start(userid, username, serverInfo);
    }

    private static void start(String userid,String username,IMServerInfo serverInfo) {
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
        ChannelFuture future = bootstrap.connect(serverInfo.getHost(), serverInfo.getNettyPort()).addListener(new ChannelFutureListener() {
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
