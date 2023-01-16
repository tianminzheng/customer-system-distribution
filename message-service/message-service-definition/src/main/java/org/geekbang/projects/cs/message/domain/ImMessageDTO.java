package org.geekbang.projects.cs.message.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ImMessageDTO implements Serializable {

    private Long id;
    private Long fromUserId;
    private String fromUsername;
    private Long toUserId;
    private String toUsername;
    private String businessTypeCode;
    private String businessTypeName;
    private String message;
    private LocalDateTime createTime;
}
