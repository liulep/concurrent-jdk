package com.liulep;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 通过Executors工具类创建线程
 */
public class ExecutorsTest {

    private static ExecutorService threadPool;

    static {
        threadPool = Executors.newFixedThreadPool(3);
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
