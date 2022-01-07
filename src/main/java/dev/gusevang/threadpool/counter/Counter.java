package dev.gusevang.threadpool.counter;

public class Counter implements Runnable {

    private int num;

    public Counter(int n) {
        num = n;
    }

    public void run() {
        System.out.println("Task " + num + " is running.");
    }
}
