package com.liujy.demo.util.niodemo;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimerServer implements Runnable {
    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;

    /**
     *  初始化
     * @param port
     */
    public MultiplexerTimerServer(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The Time server is start in port："+port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()){
                   key = iterator.next();
                   iterator.remove();
                   try{
                       handeInput(key);

                   }catch (Exception e1){
                       if(key!=null){
                           key.cancel();
                           if(key.channel()!=null){
                               key.channel().close();
                           }
                       }
                   }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(selector!=null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handeInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            //处理接入的新请求
            if(key.isAcceptable()){
                ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                try {
                    SocketChannel accept = channel.accept();
                    accept.configureBlocking(false);
                    //注册读请求到selector
                    accept.register(selector,SelectionKey.OP_READ);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //处理需要读的请求
            if(key.isReadable()){
                SocketChannel channel = (SocketChannel)key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int read = channel.read(readBuffer);
                if(read>0){
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("the time server recive order"+body);//QUERY TIME ORDER
                    String current = "QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
                    doWrite(channel,current);
                }else if(read<0){
                    key.cancel();
                    selector.close();
                }else{
                    //没有读到字节
                }
            }
        }
    }

    private void doWrite(SocketChannel channel,String response) throws IOException {
        if(!StringUtils.isEmpty(response)){
            byte[] bytes = response.getBytes();
            System.out.println(response);
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            byteBuffer.flip();
            channel.write(byteBuffer);
        }
    }
}
