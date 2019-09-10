package com.liujy.demo.timer;

import java.util.Timer;
import java.util.TimerTask;

public class RunTask {
    private static Timer timer = new Timer();
    static  public class MyTask1 extends TimerTask{
        @Override
        public void run() {
            System.out.println("运行。。。"+System.currentTimeMillis());
            int l = 1/0;
        }
    }

    static  public class MyTask2 extends TimerTask{
        @Override
        public void run() {
            System.out.println("运行。。。"+System.currentTimeMillis());
        }
    }
    public static void main(String[] args){
        MyTask1 task1 = new MyTask1();
        MyTask1 task2 = new MyTask1();
        System.out.println("开始"+System.currentTimeMillis());
        timer.schedule(task1,1000);
        timer.schedule(task2,20000);
        System.out.println("结束"+System.currentTimeMillis());
    }
}
