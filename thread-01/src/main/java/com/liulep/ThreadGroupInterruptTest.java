package com.liulep;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 中断线程组中的线程
 */
public class ThreadGroupInterruptTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadGroup threadGroup = new ThreadGroup("threadGroup");
        System.out.println("创建并启动所有线程组 :" + new Date());
        IntStream.range(0, 5).forEach(i -> {
            //将线程都添加到线程组中
            new Thread(threadGroup, () -> {
                while(!Thread.currentThread().isInterrupted()){}
                System.out.println("子线程"+Thread.currentThread().getName()+"被中断 "+ new Date());
            }).start();
        });
        //主线程休眠5秒钟
        TimeUnit.SECONDS.sleep(5);
        System.out.println("主线程中断子线程");
        //使用线程组批量中断线程
        threadGroup.interrupt();
    }
}
