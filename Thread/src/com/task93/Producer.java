package com.task93;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private final BlockingQueue<Integer> buffer;

    public Producer(BlockingQueue<Integer> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int number;
        Random random = new Random();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                number = random.nextInt(1000);
                buffer.put(number);
                System.out.println("Добавлено число: " + number + " Количество чисел в буфере: " + buffer.size());
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}
