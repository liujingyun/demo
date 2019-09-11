package com.liujy.demo.util.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.io.IOException;

public class TimeClient {
    public void connect(int port,String host) {
        //配置客户端nio线程组
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
//        byte[] bytes = "Query time order".getBytes();
//        ByteBuf buffer = Unpooled.buffer(bytes.length);
//        buffer.writeBytes(bytes);
        bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                socketChannel.pipeline().addLast(new StringDecoder());
                socketChannel.pipeline().addLast(new TimeClientHandler());
            }
        });
        try {
            //发起异步连接
            ChannelFuture f = bootstrap.connect(host, port).sync();
            //等待客户端链路
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放NIO线程组
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws IOException {
        new TimeClient().connect(8088,"127.0.0.1");
    }
}
