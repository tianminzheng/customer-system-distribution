package org.geekbang.projects.cs.im.packet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.geekbang.projects.cs.im.protocol.Command;
import org.geekbang.projects.cs.im.protocol.Packet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestPacket extends Packet {

    private String userId;
    private String userName;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

}
