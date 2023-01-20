package org.geekbang.projects.cs.search.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "elasticsearch.index")
public class EsIndexProerties {

    /**
     * 客服自动回复索引
     */
    private String customerAutoReplyIndex;
}
