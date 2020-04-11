package com.sc.netty.handler;

import com.sc.netty.codec.request.LoginRequestPacket;
import com.sc.netty.codec.response.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println("客户端开始登录...");
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setSuccess(true);
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }
}
