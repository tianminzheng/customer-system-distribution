package org.geekbang.projects.cs.im.controller;

import org.geekbang.projects.cs.im.dto.IMServerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serverInfo")
public class ServerInfoController {

    private static Logger logger = LoggerFactory.getLogger(ServerInfoController.class);

    @GetMapping("/")
    public String getServiceInfo() {

        //模拟服务器信息
        IMServerInfo imServerInfo = new IMServerInfo();
        imServerInfo.setHost("127.0.0.1");
        imServerInfo.setNettyPort(8888);
        imServerInfo.setHttpPort(9001);

        //先不用注册中心，模拟服务器地址

        return imServerInfo.toString();
    }

}
