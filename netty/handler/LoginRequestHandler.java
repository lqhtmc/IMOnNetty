package com.sc.netty.handler;

import com.sc.netty.codec.request.LoginRequestPacket;
import com.sc.netty.codec.response.LoginResponsePacket;
import com.sc.netty.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println("客户端开始登录...");
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setSuccess(true);
        LoginUtil.markAsLogin(channelHandlerContext.channel());//服务端和客户端都需要标记登录状态为已登录
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }
}
