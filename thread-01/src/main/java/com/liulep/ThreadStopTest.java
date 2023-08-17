package com.liulep;

import java.util.Date;

/**
 * 线程终止操作
 */
public class ThreadStopTest {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("子线程开始");
            while (true) {
            }
        });
        System.out.println("启动子线程："+ new Date());
        thread.start();
        //保证子线程启动
        Thread.sleep(5000);
        System.out.println("强制退出子线程："+new Date());
        thread.stop();
    }
}
