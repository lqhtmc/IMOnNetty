package com.sc.netty;

import com.sc.netty.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * netty服务端
 */
public class NettyServer {
    public static void main(String[] args) {
        //定义线程模型：bossGroup线程组用于接收连接，workerGroup线程组用于处理具体的业务
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        //服务端启动引导类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)//线程模型
                .channel(NioServerSocketChannel.class)//io模型：定义采用nio方式
                .handler(new ChannelInitializer<NioServerSocketChannel>() {//定义服务端启动过程中一些处理逻辑，非必须
                    protected void initChannel(NioServerSocketChannel nioServerSocketChannel) throws Exception {
                        System.out.println("服务端启动中");
                    }
                })
                .childHandler(new ChannelInitializer<NioSocketChannel>() {//定义后续每条连接的数据读写、业务处理逻辑
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        //nioSocketChannel.pipeline().addLast(new ServerHandler());
                        nioSocketChannel.pipeline().addLast(new SpliterDecoder());
                        nioSocketChannel.pipeline().addLast(new PacketDecoder());
                        nioSocketChannel.pipeline().addLast(new LoginRequestHandler());
                        nioSocketChannel.pipeline().addLast(new MessageRequestHandler());
                        nioSocketChannel.pipeline().addLast(new PacketEncoder());
                    }
                })
                .attr(AttributeKey.newInstance("ServerName"), "NettyServer")//给服务端channel定义一些属性，非必须
                .childAttr(AttributeKey.newInstance("EveryBizThreadName"), "BizTread")//给每一条连接定义一些属性，非必须
                .option(ChannelOption.SO_BACKLOG, 1024)//给服务端channel定义一些tcp底层属性
                .childOption(ChannelOption.SO_KEEPALIVE, true)//给每一条连接定一些tcp底层相关的属性
                .childOption(ChannelOption.TCP_NODELAY, true);

        bind(serverBootstrap, 8080);

    }

    /**
     * 服务端绑定启动端口，如果绑定失败，换个端口重试
     * @param serverBootstrap
     * @param port
     */
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(future.isSuccess()) {
                    System.out.println("服务端绑定端口成功, port = " + port);
                } else {
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}
