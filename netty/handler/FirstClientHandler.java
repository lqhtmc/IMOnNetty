package com.sc.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.UnsupportedEncodingException;

/**
 * 第一个客户端处理器
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端写出数据");
        //获取数据
        ByteBuf buffer = getByteBuffer(ctx);
        //写数据
        ctx.channel().writeAndFlush(buffer);
    }

    private ByteBuf getByteBuffer(ChannelHandlerContext ctx) {
        ByteBuf buffer = ctx.alloc().buffer();
        String str = "server，你好！";
        byte[] bytes = new byte[0];
        try {
            bytes = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        buffer.writeBytes(bytes);
        return buffer;
    }
}
