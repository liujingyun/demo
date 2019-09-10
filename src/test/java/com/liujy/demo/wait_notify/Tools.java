package com.liujy.demo.wait_notify;

public class Tools {
    volatile private boolean preIsA = false;
    synchronized public void backa(){
        try {
            while (preIsA){
                wait();
            }
            System.out.println("☆☆☆☆");
            preIsA = true;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public void backb(){
        try {
            while (!preIsA){
                wait();
            }
            System.out.println("★★★★");
            preIsA = false;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
