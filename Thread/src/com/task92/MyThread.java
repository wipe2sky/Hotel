package com.task92;

public class MyThread implements Runnable{
    @Override
    public void run() {
        Thread current = Thread.currentThread();
        while (!current.isInterrupted()) {
                System.out.println(current.getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                current.interrupt();
            }
        }
    }
}
