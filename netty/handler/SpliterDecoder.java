package com.sc.netty.handler;

import com.sc.netty.codec.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class SpliterDecoder extends LengthFieldBasedFrameDecoder {
    //根据我们的协议，长度字段之前的字节数，即需要多少个字节长度才能到长度字段
    private static final int LENGTH_FIELD_OFFSET = 7;
    //长度字段所占用的字节总数
    private static final int LENGTH_FIELD_LENGTH = 4;

    public SpliterDecoder() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        //对比客户端输入的第一个字节是否是协议定义的魔术，以此来尽快拒绝掉不满足协议的请求
        if(in.getInt(in.readerIndex()) != PacketCodeC.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
