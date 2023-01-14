package org.geekbang.projects.cs.im.packet;

import org.geekbang.projects.cs.im.protocol.Command;
import org.geekbang.projects.cs.im.protocol.Packet;


public class HeartBeatPacket extends Packet {

    private String msg = "heart-beat";

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT;
    }
}
