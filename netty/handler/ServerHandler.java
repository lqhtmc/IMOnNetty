package com.sc.netty.handler;

import com.sc.netty.codec.Packet;
import com.sc.netty.codec.PacketCodeC;
import com.sc.netty.codec.request.LoginRequestPacket;
import com.sc.netty.codec.request.MessageRequestPacket;
import com.sc.netty.codec.response.LoginResponsePacket;
import com.sc.netty.codec.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 服务端处理器
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("客户端开始登录...");
        ByteBuf byteBuf = (ByteBuf)msg;
        //解码
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        //判断请求类型，进行不同处理
        if(packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            // 登录校验
            if (valid(loginRequestPacket)) {
                // 校验成功
                LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
                loginResponsePacket.setVersion(packet.getVersion());
                if (valid(loginRequestPacket)) {
                    loginResponsePacket.setSuccess(true);
                } else {
                    loginResponsePacket.setReason("账号密码校验失败");
                    loginResponsePacket.setSuccess(false);
                }
                ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
                ctx.channel().writeAndFlush(responseByteBuf);
            } else {
                // 校验失败
            }
        } else if(packet instanceof MessageRequestPacket) {
            MessageRequestPacket msgRequestPacket = (MessageRequestPacket)packet;
            System.out.println("服务端收到客户端消息：" + msgRequestPacket.getMessage());
            MessageResponsePacket msgResponsePacket = new MessageResponsePacket();
            msgResponsePacket.setMessage("服务端回复：" + msgRequestPacket.getMessage());
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), msgResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
