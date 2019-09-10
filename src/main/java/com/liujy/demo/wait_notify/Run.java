package com.liujy.demo.wait_notify;

public class Run {
    public static void main(String[] args) {
        Tools tools = new Tools();
        for (int i =0 ; i<10;i++){
            BackA backA = new BackA(tools);
            backA.start();
        }
        for (int i =0 ; i<10;i++){
            BackB backB = new BackB(tools);
            backB.start();
        }
    }
}
