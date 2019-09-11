package com.liujy.demo.util.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

//    private final ByteBuf firstMessage;

    private int count;

    private byte[] req;
    public TimeClientHandler(ByteBuf firstMessage) {
//        this.firstMessage = firstMessage;
        firstMessage.writableBytes();
    }

    public TimeClientHandler() {
        req = ("query time order "+System.getProperty("line.separator")).getBytes();
//        firstMessage = Unpooled.copiedBuffer(req);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf messsage = null;
        for (int i=0;i<100;i++){
            messsage = Unpooled.buffer(req.length);
            messsage.writeBytes(req);
            ctx.writeAndFlush(messsage);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String s = new String(req, "UTF-8");
        System.out.println("now is "+s +"the count"+ ++count);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
