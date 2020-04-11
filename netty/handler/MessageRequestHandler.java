package com.sc.netty.handler;

import com.sc.netty.codec.request.MessageRequestPacket;
import com.sc.netty.codec.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        System.out.println("服务端收到客户端消息: " + messageRequestPacket.getMessage());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());
        channelHandlerContext.channel().writeAndFlush(messageResponsePacket);
    }
}
