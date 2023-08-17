package com.liulep;

/**
 * 线程等待与通知
 */
public class ThreadWaitNotifyTest {

    public static void main(String[] args) throws InterruptedException {
        final Object obj = new Object();
        Thread thread = new Thread(() -> {
            System.out.println("子线程名称:" + Thread.currentThread().getName());
            System.out.println("子线程开始等待");
            synchronized (obj) {
                try {
                    obj.wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("子线程被唤醒");
        });
        thread.start();
        //保证子线程先被启动
        Thread.sleep(500);
        System.out.println("主线程开始通知子线程");
        synchronized (obj){
            obj.notify();
        }
        System.out.println("主线程名称:" + Thread.currentThread().getName());
    }
}
