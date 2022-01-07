package dev.gusevang.threadpool;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executor;

public class ThreadPool implements Executor {
    /*private final int nThreads;
    private final PoolWorker[] threads;
    private final LinkedBlockingQueue queue;
    private CountDownLatch doneSignal  = new CountDownLatch(1);

    public ThreadPool(int nThreads) {
        this.nThreads = nThreads;
        queue = new LinkedBlockingQueue();
        threads = new PoolWorker[nThreads];

        for (int i = 0; i < nThreads; i++) {
            threads[i] = new PoolWorker();
            threads[i].start();
        }
    }

    public void Join(){
        try{
            for(var thread : threads){
                thread.join();
            }
        }catch(InterruptedException e){}
    }

    public void execute(Runnable task) {
        synchronized (queue) {
            queue.add(task);
            doneSignal.countDown();
            //queue.notify();
            //doneSignal = new CountDownLatch(1);
        }
    }

    private class PoolWorker extends Thread {
        public void run() {
            Runnable task;

            while (true) {
                synchronized (queue) {

                    try{
                        doneSignal.await();
                    }catch (java.lang.InterruptedException e){}
                    task = (Runnable) queue.poll();
                }
                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
                }
            }
        }
    }*/

    private final Queue<Runnable> workQueue = new ConcurrentLinkedQueue<>();
    private volatile boolean isRunning = true;

    public ThreadPool(int nThreads) {
        for (int i = 0; i < nThreads; i++) {
            new Thread(new TaskWorker()).start();
        }
    }

    @Override
    public void execute(Runnable command) {
        if (isRunning) {
            workQueue.offer(command);
        }
    }

    public void shutdown() {
        isRunning = false;
    }

    private final class TaskWorker implements Runnable {

        @Override
        public void run() {
            while (isRunning) {
                Runnable nextTask = workQueue.poll();
                if (nextTask != null) {
                    nextTask.run();
                }
            }
        }
    }
}
