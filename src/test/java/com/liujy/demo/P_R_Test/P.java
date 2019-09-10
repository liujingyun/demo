package com.liujy.demo.P_R_Test;
//生产者
public class P {
    private String lock ;

    public P(String lock) {
        this.lock = lock;
    }

    public void setValue() throws InterruptedException {
        synchronized (lock){
            if(!VauleObject.value.equals("")){
                lock.wait();
            }
            VauleObject.value = System.currentTimeMillis()+"_";
            System.out.println("set的值"+VauleObject.value);
            lock.notify();
        }
    }
}
