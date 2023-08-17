package com.liulep;

import java.util.stream.IntStream;

/**
 * 线程等待结果与谦让
 */
public class ThreadJoinTest {

    private static int sum = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("子线程名称：" + Thread.currentThread().getName());
            IntStream.range(0, 1000).forEach(i -> {
                sum += i;
            });
        });
        thread.start();
        //主线程将会等待子线程解锁
        thread.join();
        System.out.println("子线程计算结果为:"+sum);
    }
}
