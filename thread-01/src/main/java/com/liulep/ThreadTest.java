package com.liulep;

/**
 * 继承Thread类创建线程
 */
public class ThreadTest {

    private static class MyThreadTask extends Thread{

        /**
         * 重写Run方法
         */
        @Override
        public void run() {
            System.out.println("子线程名称 =>>>>"+ Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        Thread thread = new MyThreadTask();
        thread.start();
        System.out.println("主线程名称 =>>>>"+Thread.currentThread().getName());
    }
}
