package com.sc.netty.handler;

import com.sc.netty.codec.Packet;
import com.sc.netty.codec.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器：java对象转出二进制流
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        PacketCodeC.INSTANCE.encode(byteBuf, packet);
    }
}
