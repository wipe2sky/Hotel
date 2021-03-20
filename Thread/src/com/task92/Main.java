package com.task92;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new MyThread());
        Thread thread2 = new Thread(new MyThread());
        thread1.start();
        Thread.sleep(20);
        thread2.start();
        Thread.sleep(2200);
        thread1.interrupt();
        thread2.interrupt();
    }
}
