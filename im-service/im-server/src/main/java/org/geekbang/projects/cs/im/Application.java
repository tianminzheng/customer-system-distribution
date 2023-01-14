package org.geekbang.projects.cs.im;

import org.geekbang.projects.cs.im.server.Server;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.geekbang.projects.cs.im.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        Server.start();
    }
}
