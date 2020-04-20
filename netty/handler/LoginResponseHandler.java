package com.sc.netty.handler;

import com.sc.netty.Session;
import com.sc.netty.codec.response.LoginResponsePacket;
import com.sc.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭...");
    }

    /*@Override
    public void channelActive(ChannelHandlerContext ctx) {
        //System.out.println("客户端开始登录...");
        // 登录请求
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("kkk");
        loginRequestPacket.setPassword("pwd");
        ctx.channel().writeAndFlush(loginRequestPacket);
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) {
        String userId = loginResponsePacket.getUserId();
        String userName = loginResponsePacket.getUserName();
        if (loginResponsePacket.isSuccess()) {
            //LoginUtil.markAsLogin(ctx.channel());
            //System.out.println("客户端登录成功...");
            System.out.println("[" + userName + "]登录成功，userId 为: " + loginResponsePacket.getUserId());
            SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
        } else {
            //System.out.println("客户端登录失败...，原因：" + loginResponsePacket.getReason());
            System.out.println("[" + userName + "]登录失败，原因：" + loginResponsePacket.getReason());
        }
    }
}
