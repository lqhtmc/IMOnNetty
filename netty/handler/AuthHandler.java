package com.sc.netty.handler;

import com.sc.netty.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 身份认证处理器：支持热插拔，即第一次认证通过后，将该处理器从pipeline链路中删除
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("登录状态：" + LoginUtil.isLogin(ctx.channel()));
        if(!LoginUtil.isLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if(LoginUtil.isLogin(ctx.channel())) {
            System.out.println("当前客户端已经登录过，无需再次验证登录状态，AuthHandler被移除...");
        } else {
            System.out.println("当前客户端没有登录过，强制关闭连接...");
            ctx.channel().close();
        }
    }
}
