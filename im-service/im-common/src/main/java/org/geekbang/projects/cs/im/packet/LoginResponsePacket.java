package org.geekbang.projects.cs.im.packet;

import org.geekbang.projects.cs.im.protocol.Command;

public class LoginResponsePacket extends BaseResponsePacket {
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
