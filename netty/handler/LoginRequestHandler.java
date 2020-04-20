package com.sc.netty.handler;

import com.sc.netty.Session;
import com.sc.netty.codec.request.LoginRequestPacket;
import com.sc.netty.codec.response.LoginResponsePacket;
import com.sc.netty.util.LoginUtil;
import com.sc.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println("客户端开始登录...");
        System.out.println("[" + loginRequestPacket.getUsername() + "]登录...");
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setSuccess(true);
        //自定义userId
        String userId = UUID.randomUUID().toString().split("-")[0];
        loginResponsePacket.setUserId(userId);
        loginResponsePacket.setUserName(loginRequestPacket.getUsername());
        LoginUtil.markAsLogin(channelHandlerContext.channel());//服务端和客户端都需要标记登录状态为已登录
        SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUsername()), channelHandlerContext.channel());
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        SessionUtil.unBindSession(ctx.channel());
    }
}
