package com.liulep;

/**
 * Runnable实现类
 */
public class RunnableTest {

    /**
     * 当实现类是一次性类时，可以使用匿名内部类的形式创建线程
     */
    public Thread createThreadByRunnableAnonymousClass(){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程名称 =>>>>"+ Thread.currentThread().getName());
            }
        });
    }

    /**
     * 通过lambda表达式创建线程
     */
    public Thread createThreadByRunnableLambda(){
        return new Thread(() -> {
            System.out.println("子线程名称 =>>>>"+ Thread.currentThread().getName());
        });
    }

    /**
     * 通过Runnable实现类的方式
     */
    private static class MyRunnableTask implements Runnable{

        @Override
        public void run() {
            System.out.println("子线程名称 =>>>>"+ Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        new RunnableTest().createThreadByRunnableAnonymousClass().start();
        System.out.println("主线程名称 =>>>>"+Thread.currentThread().getName());

        new RunnableTest().createThreadByRunnableLambda().start();
        System.out.println("主线程名称 =>>>>"+Thread.currentThread().getName());

        Thread thread = new Thread(new MyRunnableTask());
        thread.start();
        System.out.println("主线程名称 =>>>>"+Thread.currentThread().getName());
    }
}
