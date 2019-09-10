package com.liujy.demo.wait_notify;

public class BackA extends Thread {
    private Tools tools;

    public BackA(Tools tools) {
        this.tools = tools;
    }

    @Override
    public void run() {

            this.tools.backa();
    }
}
