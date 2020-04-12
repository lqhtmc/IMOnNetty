package com.sc.netty.handler;

import com.sc.netty.codec.request.LoginRequestPacket;
import com.sc.netty.codec.response.LoginResponsePacket;
import com.sc.netty.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭...");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("客户端开始登录...");
        // 登录请求
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("kkk");
        loginRequestPacket.setPassword("pwd");
        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) {
        if (loginResponsePacket.isSuccess()) {
            LoginUtil.markAsLogin(ctx.channel());
            System.out.println("客户端登录成功...");
        } else {
            System.out.println("客户端登录失败...，原因：" + loginResponsePacket.getReason());
        }
    }
}
