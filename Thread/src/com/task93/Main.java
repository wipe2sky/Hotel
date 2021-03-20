package com.task93;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer();

        Thread producer = new Thread(new Producer(buffer.getNumbers()));
        Thread consumer = new Thread(new Consumer(buffer.getNumbers()));
        producer.start();
        consumer.start();

        Thread.sleep(5000);
        producer.interrupt();
        consumer.interrupt();
    }
}
