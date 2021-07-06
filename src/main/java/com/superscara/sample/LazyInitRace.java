package com.superscara.sample;

import net.jcip.annotations.NotThreadSafe;

import java.util.concurrent.ThreadLocalRandom;

@NotThreadSafe
public class LazyInitRace {
    private Object instance = null;

    public Object getInstance() throws InterruptedException {
        if (instance == null) {
            Thread.sleep(Math.abs(ThreadLocalRandom.current().nextInt())%1000);
            instance = new Object();
        }
        return instance;
    }
}
