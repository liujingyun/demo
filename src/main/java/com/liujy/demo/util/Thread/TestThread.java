package com.liujy.demo.util.Thread;

public class TestThread {
   public static void main(String[] args) throws InterruptedException {
       MyThread myThread = new MyThread();
       myThread.start();
       Thread.sleep(2000);
       myThread.interrupt();//打断
   }
}

class MyThread extends Thread{
    @Override
    public void run() {
        while(true){
            if(this.isInterrupted()){
                System.out.println("停止了。。。");
                return;
            }
            System.out.println("timer："+System.currentTimeMillis());
        }
    }
}
