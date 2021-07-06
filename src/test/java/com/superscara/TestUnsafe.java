package com.superscara;

import com.superscara.sample.LazyInitRace;
import com.superscara.sample.LoggingWidget;
import com.superscara.sample.Sequence;
import com.superscara.sample.Widget;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class TestUnsafe {
    int count = 0;
    AtomicInteger count2 = new AtomicInteger(0);

    @Test
    void test() throws InterruptedException {
        Sequence sequence = new Sequence();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 500000; i++) {
                count++;
                count2.getAndIncrement();
                sequence.getNext();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 500000; i++) {
                count++;
                count2.getAndIncrement();
                sequence.getNext();
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        Assertions.assertTrue(1000000 > count);
        Assertions.assertEquals(1000000, count2.get());
        Assertions.assertEquals(1000001, sequence.getNext());
    }

    @Test
    void testInitSingleton() throws InterruptedException {
        LazyInitRace lazyInitRace = new LazyInitRace();
        AtomicReference<Object> instance1 = new AtomicReference<>();
        AtomicReference<Object> instance2 = new AtomicReference<>();
        Thread thread1 = new Thread(() -> {
            try {
                instance1.set(lazyInitRace.getInstance());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                instance2.set(lazyInitRace.getInstance());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        Assertions.assertNotEquals(instance1.get(), instance2.get());
    }

    @Test
    void testReentrantLock() throws InterruptedException {
        LoggingWidget loggingWidget = new LoggingWidget();
        Widget widget = new Widget();
        Thread thread1 = new Thread(() -> {
            try {
                loggingWidget.doSomething("Logging");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                widget.doSomething("Widget");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }

}
