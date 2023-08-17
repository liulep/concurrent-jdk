package com.liulep;

/**
 *  线程挂起与执行测试
 */
public class ThreadSuspendAndResumeTest {

    public static void main(String[] args) throws InterruptedException {
        final Object obj = new Object();
        Thread thread = new Thread(() -> {
            System.out.println("子线程名称：" + Thread.currentThread().getName());
            synchronized (obj) {
                System.out.println("子线程挂起");
                Thread.currentThread().suspend();
            }
            System.out.println("子线程被唤醒");
        });
        thread.start();
        //保证子线程启动
        Thread.sleep(500);
        System.out.println("主线程通知子线程继续执行");
        thread.resume();
        System.out.println("主线程名称：" + Thread.currentThread().getName());
    }
}
