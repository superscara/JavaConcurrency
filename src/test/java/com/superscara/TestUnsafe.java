package com.superscara;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class TestUnsafe {
    int count = 0;
    AtomicInteger count2 = new AtomicInteger(0);

    @Test
    void test() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                count++;
                count2.getAndIncrement();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                count++;
                count2.getAndIncrement();
            }
        });
        thread1.start();
        thread2.start();
        Thread.currentThread().sleep(30000);
        Assertions.assertNotEquals(100000, count);
        Assertions.assertEquals(100000, count2.get());


    }

}
