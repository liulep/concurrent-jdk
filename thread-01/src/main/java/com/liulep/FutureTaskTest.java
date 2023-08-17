package com.liulep;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTaskTest配合Thread
 */
public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> stringFutureTask = new FutureTask<>(() -> {
            return "使用FutureTask配合Thread的方式创建线程";
        });
        new Thread(stringFutureTask).start();
        System.out.println(stringFutureTask.get());
    }
}
