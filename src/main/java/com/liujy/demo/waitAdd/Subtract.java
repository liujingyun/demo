package com.liujy.demo.waitAdd;

public class Subtract {
    private String lock;
    public Subtract(String lock) {
        this.lock = lock;
    }

    public void subtract(){
        try {
            synchronized (lock){
               // if (ValueObject.list.size()==0){/
                while (ValueObject.list.size()==0){ //解决方案 自旋？
                    System.out.println("wait begin。。"+Thread.currentThread().getName());
                    lock.wait();
                    System.out.println("end begin。。"+Thread.currentThread().getName());

                }
                ValueObject.list.remove(0);
                System.out.println("list size"+ValueObject.list.size());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
