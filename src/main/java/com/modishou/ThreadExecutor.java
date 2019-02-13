package com.modishou;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: modishou
 * @Date: 2019/1/13 20:14
 */
public class ThreadExecutor {

    private LinkedBlockingDeque<Runnable> taskQueue;
    private final List<Thread> threads = new ArrayList<>();
    private int coreSize = 0;
    private int poolSize;
    private volatile boolean RUNNING = true;

    public ThreadExecutor(int poolSize) {
        this.poolSize = poolSize;
        taskQueue = new LinkedBlockingDeque<>();
    }

    public void execute(Runnable task) {
        if (coreSize < poolSize) {
            addTask(task);
        } else {
            taskQueue.offer(task);
        }
    }

    private void addTask(Runnable task) {
        coreSize ++;
        Worker worker = new Worker(task);
        Thread thread = new Thread(worker);
        threads.add(thread);
        thread.start();
    }


    private class Worker implements Runnable {

        public Worker(Runnable task) {
            taskQueue.offer(task);
        }

        @Override
        public void run() {
            while (RUNNING) {
                if (!taskQueue.isEmpty()) {
                    try {
                        Runnable task = taskQueue.take();
                        task.run();
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void shutdown() throws Exception{
        //队列未被取空稍微等一下
        if (!taskQueue.isEmpty()) {
            Thread.sleep(100);
        }
        RUNNING = false;
        for (Thread t : threads) {
            t.interrupt();
            System.out.println("interrupt:" + t.getName());
        }
        Thread.currentThread().interrupt();
    }

    public static void main(String[] args) throws Exception{
        ThreadExecutor pool = new ThreadExecutor(3);
        for (int i=0; i<5; i++) {
            MyThread t = new MyThread();
            pool.execute(t);
        }
        pool.shutdown();
        System.out.println("main");
    }
}
