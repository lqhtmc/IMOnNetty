package com.sc.netty.handler;

import com.sc.netty.Session;
import com.sc.netty.codec.request.MessageRequestPacket;
import com.sc.netty.codec.response.MessageResponsePacket;
import com.sc.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
        // 拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(channelHandlerContext.channel());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        System.out.println("服务端收到客户端消息: " + messageRequestPacket.getMessage());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        // 拿到消息接收方的 channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
        }
    }
}
