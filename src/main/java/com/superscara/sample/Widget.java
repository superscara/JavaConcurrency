package com.superscara.sample;

import java.util.concurrent.ThreadLocalRandom;

public class Widget {
    public synchronized void doSomething(String name) throws InterruptedException {
        Thread.sleep(Math.abs(ThreadLocalRandom.current().nextInt())%1000);
        System.out.println(name + " do something");
    }
}
