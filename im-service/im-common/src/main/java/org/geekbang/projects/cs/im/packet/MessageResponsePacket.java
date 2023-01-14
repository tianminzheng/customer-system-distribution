package org.geekbang.projects.cs.im.packet;

import lombok.Data;
import org.geekbang.projects.cs.im.protocol.Command;

@Data
public class MessageResponsePacket extends BaseResponsePacket {

    /**
     * 响应内容
     */
    private String message;

    /**
     * 消息来源
     */
    private String fromUserId;
    private String fromUserName;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
