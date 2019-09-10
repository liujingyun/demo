package com.liujy.demo.wait_notify;

public class BackB extends Thread {
    private Tools tools;

    public BackB(Tools tools) {
        this.tools = tools;
    }

    @Override
    public void run() {
        tools.backb();
    }
}
