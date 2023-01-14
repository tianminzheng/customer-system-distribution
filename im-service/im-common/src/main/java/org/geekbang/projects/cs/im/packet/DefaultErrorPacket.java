package org.geekbang.projects.cs.im.packet;

import org.geekbang.projects.cs.im.protocol.Command;

public class DefaultErrorPacket extends BaseResponsePacket {

    @Override
    public Byte getCommand() {
        return Command.DEFAULT_ERROR;
    }
}
