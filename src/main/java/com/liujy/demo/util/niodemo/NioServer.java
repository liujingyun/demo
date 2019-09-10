package com.liujy.demo.util.niodemo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class NioServer {
    private static int port = 21103;
    public static void main(String[] args) throws IOException {
        //1.打开ServerSocketChannel 用于监听客户端 连接
        ServerSocketChannel open = ServerSocketChannel.open();
        //2.绑定监听端口 设置连接非阻塞
        open.socket().bind(new InetSocketAddress(InetAddress.getByName("IP"),port));
        open.configureBlocking(false);
        //3.创建Reactor线程
        Selector selector = Selector.open();
        new Thread().start();
        //4.serverSocketChannel注册到Reactor线程
//        open.register(selector, SelectionKey.OP_ACCEPT,)
    }
}
