package com.liujy.demo.P_R_Test;

public class Run {
    public static void main(String[] args) throws InterruptedException {
        String lock = "";
        P p = new P(lock);
        ThreadP threadP = new ThreadP(p);
        threadP.start();
        C c = new C(lock);
        ThreadC threadC = new ThreadC(c);
        threadC.start();

    }
}

class ThreadP extends Thread {
    private P p;

    public ThreadP(P p) {
        this.p = p;
    }

    @Override
    public void run() {
        try {
            while (true) {

                this.p.setValue();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ThreadC extends Thread {
    private C c;

    public ThreadC(C c) {
        this.c = c;
    }

    @Override
    public void run() {
        try {
            while (true) {

                this.c.getValue();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}