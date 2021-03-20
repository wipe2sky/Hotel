package com.task93;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Buffer {
    private BlockingQueue<Integer> numbers = new ArrayBlockingQueue<Integer>(5, true);

    public BlockingQueue<Integer> getNumbers() {
        return numbers;
    }
}
