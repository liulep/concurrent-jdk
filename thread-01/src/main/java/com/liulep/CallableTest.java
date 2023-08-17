package com.liulep;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 实现Callable接口
 */
public class CallableTest {

    /**
     * 通过匿名内部类创建线程
     */
    public FutureTask<String> createThreadByCallableAnonymousClass(){
        return new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("子线程名称 =>>>>"+ Thread.currentThread().getName());
                return Thread.currentThread().getName();
            }
        });
    }

    /**
     * 通过lambda表达式创建线程
     */
    public FutureTask<String> createThreadByCallableLambda(){
        return new FutureTask<String>(() -> {
            System.out.println("子线程名称 =>>>>"+ Thread.currentThread().getName());
            return Thread.currentThread().getName();
        });
    }

    /**
     * 通过Callable实现类的方式
     */
    private static class MyCallableTask implements Callable<String>{

        @Override
        public String call() throws Exception {
            System.out.println("子线程名称 =>>>>"+ Thread.currentThread().getName());
            return Thread.currentThread().getName();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> future = new CallableTest().createThreadByCallableAnonymousClass();
        new Thread(future).start();
        System.out.println("从自线程中获取的数据为 =>>>>>>"+future.get());
        System.out.println("主线程名称 =>>>>>>>"+ Thread.currentThread().getName());

        FutureTask<String> futureByLambda = new CallableTest().createThreadByCallableLambda();
        new Thread(futureByLambda).start();
        System.out.println("从自线程中获取的数据为 =>>>>>>"+future.get());
        System.out.println("主线程名称 =>>>>>>>"+ Thread.currentThread().getName());

        FutureTask<String> stringFutureTask = new FutureTask<>(new MyCallableTask());
        new Thread(stringFutureTask).start();
        System.out.println("从自线程中获取的数据为 =>>>>>>"+future.get());
        System.out.println("主线程名称 =>>>>>>>"+ Thread.currentThread().getName());
    }
}
