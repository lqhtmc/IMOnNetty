package com.sc.netty.handler;

import com.sc.netty.codec.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解码器：作用是将二进制流数据解码成java对象。继承netty自带的处理器
 */
public class PacketDecoder extends ByteToMessageDecoder {
    /**
     * decode方法的第三个参数，将解码结果传递到下一个处理器
     * @param channelHandlerContext
     * @param byteBuf
     * @param list
     * @throws Exception
     */
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(PacketCodeC.INSTANCE.decode(byteBuf));
    }
}
