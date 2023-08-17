package com.liulep;

import java.util.concurrent.*;

/**
 * 通过ThreadPoolExecutor类来创建线程
 */
public class ThreadPoolExecutorTest {

    public static ThreadPoolExecutor threadPool;

    static {
        threadPool = new ThreadPoolExecutor(3, 3, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));
    }

    // 测试submit(Runnable task)方法
    public static void submitByRunnable(){
        System.out.println("主线程名称 =>>>>" + Thread.currentThread().getName());
        threadPool.submit(() -> {
            System.out.println("子线程名称 =>>>>> "+ Thread.currentThread().getName());
        });
    }

    // 测试submit(Callable task)方法
    public static void submitByCallable() throws ExecutionException, InterruptedException {
        System.out.println("主线程名称 =>>>>" + Thread.currentThread().getName());
        Future<String> futures = threadPool.submit(() -> {
            System.out.println("子线程名称 =>>>>> " + Thread.currentThread().getName());
            return Thread.currentThread().getName();
        });
        System.out.println("从子线程中获取的数据为 =>>>>> "+ futures.get());
    }

    // 测试execute(Runnable command)
    public static void executeByRunnable(){
        System.out.println("主线程名称 =>>>>" + Thread.currentThread().getName());
        threadPool.execute(() -> {
            System.out.println("子线程名称 =>>>>> "+ Thread.currentThread().getName());
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        submitByRunnable();
        submitByCallable();
        executeByRunnable();
    }
}
