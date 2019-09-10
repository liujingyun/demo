package com.liujy.demo.niodemo;

import com.liujy.demo.niodemo.aio.AsyncTimeClientHandler;

import java.io.IOException;

public class TimeClient {
    public static void main(String[] args) throws IOException {
//        new Thread(new TimeClientHandle("127.0.0.1",8088),"Time-Client-001").start();
        new Thread(new AsyncTimeClientHandler("127.0.0.1",8088)).start();
    }
}
