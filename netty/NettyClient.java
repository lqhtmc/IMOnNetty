package com.sc.netty;

import com.sc.netty.codec.request.LoginRequestPacket;
import com.sc.netty.codec.request.MessageRequestPacket;
import com.sc.netty.handler.*;
import com.sc.netty.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Scanner;

/**
 * netty客户端
 */
public class NettyClient {
    public static void main(String[] args) {
        //定义线程模型
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        //客户端启动引导类
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)//线程模型
                .channel(NioSocketChannel.class)//io模型：定义nio方式
                .handler(new ChannelInitializer<SocketChannel>() {//定义客户端的业务处理逻辑
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //socketChannel.pipeline().addLast(new ClientHandler());
                        socketChannel.pipeline().addLast(new SpliterDecoder());
                        socketChannel.pipeline().addLast(new PacketDecoder());
                        socketChannel.pipeline().addLast(new LoginResponseHandler());
                        socketChannel.pipeline().addLast(new MessageResponseHandler());
                        socketChannel.pipeline().addLast(new PacketEncoder());
                    }
                })
                .attr(AttributeKey.newInstance("CilentName"), "NettyClient")//给客户端channel绑定自定义属性
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)//给连接设置一些tcp底层属性
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                ;
        connect(bootstrap, "127.0.0.1", 8080);
    }

    /**
     * 客户端连接服务端，如果失败允许重试
     * @param bootstrap
     * @param host
     * @param port
     */
    private static void connect(final Bootstrap bootstrap, final String host, final int port) {
        bootstrap.connect(host, port).addListener(new GenericFutureListener<Future<? super Void>>() {
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(future.isSuccess()) {
                    System.out.println("客户端连接服务端成功");
                    Channel channel = ((ChannelFuture)future).channel();
                    startConsoleThread(channel);
                } else {
                    System.out.println("客户端连接服务端失败， time = " + System.currentTimeMillis());
                    connect(bootstrap, host, port);
                }
            }
        });
    }

    /**
     * 启动一个线程，监听输入窗口的输入
     * @param channel
     */
     public static void startConsoleThread(final Channel channel) {
         final LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
         final Scanner sc = new Scanner(System.in);
        Thread t = new Thread(new Runnable() {
            public void run() {
                while(!Thread.interrupted()) {
                    //if(LoginUtil.isLogin(channel)) {
                        /*System.out.println("请输入消息发送到服务端...");
                        Scanner sc = new Scanner(System.in);
                        String msg = sc.nextLine();

                        MessageRequestPacket packet = new MessageRequestPacket();
                        packet.setMessage(msg);
                        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc(), packet);
                        channel.writeAndFlush(byteBuf);*/
                    //}
                    if (!SessionUtil.hasLogin(channel)) {
                        System.out.print("输入用户名登录: ");
                        String username = sc.nextLine();
                        loginRequestPacket.setUsername(username);

                        // 密码使用默认的
                        loginRequestPacket.setPassword("pwd");

                        // 发送登录数据包
                        channel.writeAndFlush(loginRequestPacket);
                        waitForLoginResponse();
                    } else {
                        String toUserId = sc.next();
                        String message = sc.next();
                        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
                    }
                }
            }
        });
        t.start();
     }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
