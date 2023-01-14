package org.geekbang.projects.cs.im.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("im_message")
public class ImMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 消息发送方用户Id
     */
    private String fromUserId;

    /**
     * 消息发送方用户名
     */
    private String fromUsername;

    /**
     * 消息接收方用户Id
     */
    private String toUserId;

    /**
     * 消息接收方用户名
     */
    private String toUsername;

    /**
     * 聊天消息
     */
    private String message;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
