package com.liujy.demo.pool;

import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class ThreadPool {
    /**
     *       RUNNING:  Accept new tasks and process queued tasks
     *       SHUTDOWN: Don't accept new tasks, but process queued tasks
     *       STOP:     Don't accept new tasks, don't process queued tasks,
     *                   and interrupt in-progress tasks
     *       TIDYING:  All tasks have terminated, workerCount is zero,
     *                   the thread transitioning to state TIDYING
     *                   will run the terminated() hook method
     *       TERMINATED: terminated() has completed
     */
    private int status;
    //核心线程数
    private int corePoolSize;
    //最大线程数
    private int maximumPoolSize;
    //线程没有执行任务时最多保持多久时间会终止
    private long keepAliveTime;
    //时间单位
    private TimeUnit unit;
    //记录当前总共线程任务数
    private AtomicInteger totalTask;

    //阻塞队列
    private BlockingQueue<Runnable> workQueue;
    //
    //存放线程池
    private volatile Set<Worker> workers;


    private final class Worker
            extends AbstractQueuedSynchronizer
            implements Runnable{
        @Override
        public void run() {

        }
    }
}
