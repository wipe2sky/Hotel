package com.task94;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MyDaemon myDaemon = new MyDaemon(1000);
        myDaemon.setDaemon(true);
        myDaemon.start();
        Thread.sleep(5000);
    }
}
