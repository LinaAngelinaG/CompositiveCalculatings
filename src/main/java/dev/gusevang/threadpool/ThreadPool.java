package dev.gusevang.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executor;

public class ThreadPool {
    private final int nThreads;
    private final PoolWorker[] threads;
    private final LinkedBlockingQueue queue;
    private volatile boolean isRunning = true;

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
                isRunning = false;
            }
            for(var thread : threads){
                thread.join();
            }
        }catch(InterruptedException e){}
    }

    public void execute(Runnable task) {
        synchronized (queue) {
            queue.add(task);
            queue.notify();
        }
    }

    private class PoolWorker extends Thread {
        public void run() {
            Runnable task;

            while (true) {
                synchronized (queue) {
                    /*while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("An error occurred while queue is waiting: " + e.getMessage());
                        }
                        if(!isRunning){
                            break;
                        }
                    }
                    if(!isRunning){
                        break;
                    }*/
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        System.out.println("An error occurred while queue is waiting: " + e.getMessage());
                    }
                    if(queue.isEmpty()){
                        break;
                    }
                    task = (Runnable) queue.poll();
                }
                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
                }
            }
        }
    }

   public void shutdown() {
        isRunning = false;
    }
}
