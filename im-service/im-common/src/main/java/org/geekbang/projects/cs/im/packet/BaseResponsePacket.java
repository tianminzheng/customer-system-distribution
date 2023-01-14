package org.geekbang.projects.cs.im.packet;

import lombok.Data;
import org.geekbang.projects.cs.im.protocol.Packet;


@Data
public abstract class BaseResponsePacket extends Packet {
    /**
     * 返回码,0000表示成功
     */
    private String code = "0000";
    /**
     * 返回消息
     */
    private String msg;

    /**
     * 判断是否操作成功，0000表示成功
     */
    public boolean success(){
        return "0000".equals(code);
    }

}
