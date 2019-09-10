package com.liujy.demo.waitAdd;

public class Add {
    private String lock;

    public Add(String lock) {
        this.lock = lock;
    }

    public void add(){
        synchronized (lock){
            ValueObject.list.add("anythings");
            lock.notifyAll();
        }
    }
}
