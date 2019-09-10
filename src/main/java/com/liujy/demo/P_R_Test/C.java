package com.liujy.demo.P_R_Test;

public class C {
    private String lock;

    public C(String lock) {
        this.lock = lock;
    }

    public void getValue() throws InterruptedException {
        synchronized (lock) {
            if (VauleObject.value.equals("")) {
                lock.wait();
            }
            System.out.println("get的值" + VauleObject.value);
            lock.notify();
        }
    }
}
