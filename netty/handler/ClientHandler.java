package com.sc.netty.handler;

import com.sc.netty.codec.Packet;
import com.sc.netty.codec.PacketCodeC;
import com.sc.netty.codec.request.LoginRequestPacket;
import com.sc.netty.codec.response.LoginResponsePacket;
import com.sc.netty.codec.response.MessageResponsePacket;
import com.sc.netty.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

/**
 * 客户端处理器
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端开始登录...");
        // 登录请求
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("kkk");
        loginRequestPacket.setPassword("pwd");
        // 编码
        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);
        // 写数据
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        //解码
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if(packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()) {
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println("客户端登录成功...");
            } else {
                System.out.println("客户端登录失败...，原因：" + loginResponsePacket.getReason());
            }
        } else if(packet instanceof MessageResponsePacket){
            MessageResponsePacket msgReponsePacket = (MessageResponsePacket)packet;
            System.out.println("客户端收到服务端的响应：" + msgReponsePacket.getMessage());
        }
    }
}
