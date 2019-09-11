package com.liujy.demo.util.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.IOException;

/**
 * @ClassName WebSocketServer
 * @Description
 * @Author jingyun_liu
 * @Date 2019/9/11 17:28
 * @Version V1.0
 **/
public class WebSocketServer {
    public void run(int port) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChildChannelHandler());
        try {
            ChannelFuture sync = bootstrap.bind(port).sync();
            //等待服务端口关闭
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ChannelPipeline pipeline = socketChannel.pipeline();
            pipeline.addLast("http-codec",new HttpServerCodec());
            pipeline.addLast("aggregator",new HttpObjectAggregator(65536));

            pipeline.addLast("http-chunked",new ChunkedWriteHandler());
            pipeline.addLast("handler",new WebSocketServerProtocolHandler("/ws"));

            pipeline.addLast(new ChatHandler());
        }
    }

    public static void main(String[] args) throws IOException {
        new WebSocketServer().run(8989);
    }
}