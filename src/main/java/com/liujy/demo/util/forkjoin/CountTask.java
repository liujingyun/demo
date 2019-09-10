package com.liujy.demo.util.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 带返回值的双端任务  RecursiveTask ：有结果返回的任务 RecursiveAction ：无结果返回的任务
 */
public class CountTask extends RecursiveTask<Integer> {
    private int start;
    private int end;
    private static final int HOLD = 30;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end - start)<= HOLD;
        if(canCompute){
            //不需要拆分
            for (int i = start; i <= end ; i++) {
                sum +=i;
            }
            int i = 1;
            System.out.println("thread:"+Thread.currentThread()+"start :"+start+" end:"+end);
        } else {
            int mid = (end + start)/2;
            CountTask left = new CountTask(start,mid);
            CountTask right = new CountTask(mid+1,end);
            left.fork();//fork 让task异步执行 join让task同步执行，可以获取返回值
            right.fork();
            sum = left.join() + right.join();
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
        int start = 0;
        int end = 200;
        CountTask countTask = new CountTask(start,end);
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        ForkJoinTask<Integer> submit = forkJoinPool.submit(countTask);
        int sum = submit.get();
        System.out.println(sum);
    }
}
