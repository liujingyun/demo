package com.liujy.demo.niodemo;

import com.liujy.demo.niodemo.aio.AsyncTimeServerHandler;

import java.io.IOException;

public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 8088;
//        MultiplexerTimerServer timerServer = new MultiplexerTimerServer(port);
//        new Thread(timerServer,"NIO-Multi-001").start();
        new Thread(new AsyncTimeServerHandler(8088)).start();
    }
}
