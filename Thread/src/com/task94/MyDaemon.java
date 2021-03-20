package com.task94;

import java.time.LocalDateTime;

public class MyDaemon extends Thread{
    private int period;

    public MyDaemon(int period) {
        this.period = period;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(period);
            } catch (InterruptedException e) {
            }
            System.out.println(LocalDateTime.now());
        }

    }
}
