package dev.gusevang.threadpool.counter;

import dev.gusevang.threadpool.ThreadPool;

public class MultithreadedCount {

    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool(7);

        for (int i = 0; i < 5; i++) {
            Counter task = new Counter(i);
            pool.execute(()->{task.run();});
        }


        pool.shutdown();
    }
}


/*
ThreadPool pool = new ThreadPool(7);

        for (int i = 0; i < 5; i++) {
            Counter task = new Counter(i);
            CountDownLatch doneSignal  = new CountDownLatch(1);
            pool.execute(() -> {task.run(); doneSignal.countDown();});
            try{
                doneSignal.await();
            }catch(java.lang.InterruptedException e){}
 */