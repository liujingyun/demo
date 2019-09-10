package com.liujy.demo;

public class VolatileExample {
    private int a = 0;
    private volatile boolean flag = false;
    public void writer() throws InterruptedException {
        a = 1;          //1
        //Thread.sleep(1000l);
        flag = true;   //2
    }
    public void reader(){
        if(flag){      //3
            int i = a; //4
            System.out.println(i);
        }
    }

    public static void main(String[] args){
        VolatileExample example = new VolatileExample();
        new Thread(){
            @Override
            public void run() {
                try {
                    example.writer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        example.reader();
//       new Thread(){
//            @Override
//            public void run() {
//                example.reader();
//            }
//        }.start();
    }
}
