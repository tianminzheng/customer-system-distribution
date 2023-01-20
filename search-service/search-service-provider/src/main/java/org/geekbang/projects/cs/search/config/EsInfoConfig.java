package org.geekbang.projects.cs.search.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "elasticsearch.info")
public class EsInfoConfig {

    private String username;

    private String password;

    private String hostname;

    private int port;

    private String scheme;

}
