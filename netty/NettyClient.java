package com.sc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

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
                } else {
                    System.out.println("客户端连接服务端失败， time = " + System.currentTimeMillis());
                    connect(bootstrap, host, port);
                }
            }
        });
    }
}
