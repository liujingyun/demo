package com.liujy.demo.Thread;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 *  不可重入
 */
public class MyLock {
    /**
     * 一个当前线程状态 是否占用该锁
     */
    private volatile int state = 0;
    private static final Unsafe unsafe;
    private static final long stateOffset;
    private static final long headOffset;
    private static final long tailOffset;
    //链表头
    private volatile Node head;
    //链表尾
    private volatile Node tail;
    static final Node EMPTY = new Node();
    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe)f.get(null);
            stateOffset = unsafe.objectFieldOffset
                    (MyLock.class.getDeclaredField("state"));
            headOffset = unsafe.objectFieldOffset
                    (MyLock.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset
                    (MyLock.class.getDeclaredField("tail"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    public  MyLock(){
        head = tail =  EMPTY;
    }

    /**
     * 更新状态
     *
     * @param expect
     * @param update
     * @return
     */
    public boolean compareAndSetState(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

    /**
     * 更新尾节点
     *
     * @param expect
     * @param update
     * @return
     */
    public boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }

    /**
     * 一个等待队列 存放线程 使用链表 集合 数组都可以
     */
    private static class Node {
        /**
         * 双向队列 需要以下元素 存储的线程 前后节点
         */
        //存储线程
        Thread thread;

        Node prev;

        Node next;

        public Node() {
        }

        public Node(Thread thread, Node prev) {
            this.thread = thread;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "prv=" + prev.thread.getName() + "now:"+thread.getName()+" next:"+next.thread.getName()+
                    '}';
        }
    }

    /**
     * 加锁
     */
    public void lock() {
        //1.先判断是state是否为1
        if (compareAndSetState(0, 1)) {
            //更新成功 证明锁拿到 返回继续执行
            return;
        }
        //2.更新失败 则进入队列
        Node node = enqueue();
        Node prev = node.prev;//原来尾节点
        //再次尝试获取锁 需要检测上一个节点是不是head 按入队顺序加锁
        while (node.prev != head || !compareAndSetState(0, 1)) {
            //未获取锁 阻塞
            unsafe.park(false, 0L);
        }
        //head 移位
        head = node;
        node.thread = null;
        node.prev = null;
        prev.next = null;

    }

    private Node enqueue() {
        while (true) {
            //获取尾结点
            Node t = tail;
            Node node = new Node(Thread.currentThread(), t);
            //不断尝试原子更新尾节点
            if (compareAndSetTail(t, node)) {
                t.next = node;
                return node;
            }

        }
    }

    /**
     * 解锁
     */
    public void unlock() {
        //
        state = 0;
        //下一个待唤醒节点
        Node next = head.next;
        if (next != null) {
            unsafe.unpark(next.thread);
        }
    }
    private static int count = 0;
    public static void main(String[] args) throws InterruptedException {
        MyLock myLock = new MyLock();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        IntStream.range(0, 10).forEach(i -> new Thread(() -> {
//            synchronized (String.class){
//                IntStream.range(0,1000).forEach(j->{
//                    synchronized (String.class){
//                        count++;
//                    }
//                });
//            }

            try {
                myLock.lock();
                IntStream.range(0,1000).forEach(j->{
                    count++;
                });
            } finally {
                myLock.unlock();
            }
            countDownLatch.countDown();
        },"t-"+ i).start());
        countDownLatch.await();
        System.out.println(count);
    }
}
