package com.superscara.sample;

public class LoggingWidget extends Widget {
    public synchronized void doSomething(String name) throws InterruptedException {
        System.out.println(toString() + ": call doSomething");
//        Thread.sleep(1000);
        super.doSomething(name);
    }
}
