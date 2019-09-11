package com.liujy.demo.util.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;


/**
 * @ClassName ChatHandler
 * @Description
 * @Author jingyun_liu
 * @Date 2019/9/11 17:47
 * @Version V1.0
 **/
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    //用于记录和管理所有客户端的channle
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端传输过来的信息
        String content = msg.text();
        System.out.println("接收到的数据：" + content);

        for(Channel channel : clients) {
            channel.writeAndFlush(new TextWebSocketFrame("[服务器在]" + LocalDateTime.now()
                    + "接收到消息，消息为：" + content));
        }
    }

    /**
     * 当客户端连接服务端之后（打开连接）
     * 获取客户端的channle,并且放到ChannelGroup中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //当触发handlerRemoved,ChannelGroup会自动移除客户端的channel
        //clients.remove(ctx.channel())
        System.out.println("客户端断开，channel对应的长id为：" + ctx.channel().id().asLongText());
        System.out.println("客户端断开，channel对应的短id为：" + ctx.channel().id().asShortText());
    }
}
