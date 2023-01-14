package org.geekbang.projects.cs.im.handler;

import org.geekbang.projects.cs.im.protocol.Packet;
import org.geekbang.projects.cs.im.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List list) throws Exception {
        Packet packet = PacketCodeC.getInstance().decode(byteBuf);
        list.add(packet);
    }
}
