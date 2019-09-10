package com.liujy.demo.waitAdd;

public class run {
    public static void main(String[] args) throws InterruptedException {
        String lock = "";
        Add add = new Add(lock);

        Subtract subtract = new Subtract(lock);

        ThreadSubtract threadSubtract1 = new ThreadSubtract(subtract);
        threadSubtract1.setName("subtractThread1");
        threadSubtract1.start();
        ThreadSubtract threadSubtract2 = new ThreadSubtract(subtract);
        threadSubtract2.setName("subtractThread2");
        threadSubtract2.start();
        Thread.sleep(1000L);
        ThreadAdd threadAdd = new ThreadAdd(add);
        threadAdd.setName("addThread");
        threadAdd.start();

    }
}

class ThreadAdd extends Thread{
    private Add add;

    public ThreadAdd(Add add) {
        this.add = add;
    }

    @Override
    public void run() {
        add.add();
    }
}

class ThreadSubtract extends Thread{
    private Subtract subtract;

    public ThreadSubtract(Subtract subtract) {
        this.subtract = subtract;
    }

    @Override
    public void run() {
        subtract.subtract();
    }
}
