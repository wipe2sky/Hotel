package com.task93;

import java.util.concurrent.BlockingQueue;

public class Consumer implements  Runnable{
    private final BlockingQueue<Integer> buffer;

    public Consumer(BlockingQueue<Integer> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int number = buffer.take();
                System.out.println("Забрали число: " + number + " Количество чисел в буфере: " + buffer.size());
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
