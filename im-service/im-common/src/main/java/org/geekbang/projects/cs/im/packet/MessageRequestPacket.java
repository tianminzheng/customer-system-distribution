package org.geekbang.projects.cs.im.packet;

import lombok.Data;
import org.geekbang.projects.cs.im.protocol.Command;
import org.geekbang.projects.cs.im.protocol.Packet;

@Data
public class MessageRequestPacket extends Packet {

    /**
     * 消息内容
     */
    private String message;


    /**
     * 消息接受者
     */
    private String toUserId;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
