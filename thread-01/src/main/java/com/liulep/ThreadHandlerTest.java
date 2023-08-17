package com.liulep;

import java.sql.SQLOutput;
import java.util.Date;

/**
 * 线程设置
 */
public class ThreadHandlerTest {

    //通过构造方法对线程设置名称
    private static void setNameByThread(){
        Thread thread = new Thread(() -> {
            System.out.println("子线程名称：" + Thread.currentThread().getName());
        }, "线程1");
        thread.start();
        System.out.println("主线程名称：" + Thread.currentThread().getName());
    }

    //通过Thread类里的方法对线程设置名称
    //public final synchronized void setName(String name)
    private static void setNameByMethod(){
        Thread thread = new Thread(() -> {
            System.out.println("子线程名称：" + Thread.currentThread().getName());
        });
        thread.setName("线程2");
        thread.start();
        System.out.println("主线程名称：" + Thread.currentThread().getName());
    }

    //通过Thread类的构造方法对线程指定线程组的方法
    private static void threadGroup(){
        Thread thread = new Thread(new ThreadGroup("liulep-group"), () -> {
            System.out.println("子线程名称：" + Thread.currentThread().getName());
        });
        thread.start();
        System.out.println("主线程名称：" + Thread.currentThread().getName());
        System.out.println("子线程所在的线程组名称：" + thread.getThreadGroup().getName());
    }

    /**
     * 设置线程优先级：默认（缺省）值为5，最小为1，最大为10
     * public final void setPriority(int newPriority);
     */

    //线程睡眠
    private static void threadSleep(){
        new Thread(() -> {
            System.out.println("当前时间为:"+ new Date());
            System.out.println("子线程名称："+ Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
            System.out.println("当前时间为:"+ new Date());
        }).start();
        System.out.println("主线程名称："+Thread.currentThread().getName());
    }

    // 中断睡眠中的线程
    public static void interruptSleepThread() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("子线程名称：" + Thread.currentThread().getName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                //中断休眠中的线程会抛出异常，并清空中断标记,线程此时却没有中断，需要重新设置锁标记。
                //System.out.println("中断休眠中的线程会抛出异常，并清空中断标记");
                System.out.println("线程中断状态；"+Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt();
            }
        });
        thread.start();
        //保证子线程顺利执行
        Thread.sleep(500);
        System.out.println("在主线程中中断子线程");
        thread.interrupt();
        System.out.println("主线程名称："+ Thread.currentThread().getName());
    }

    //中断正在运行的线程
    public static void interruptRunningThread() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("子线程名称：" + Thread.currentThread().getName());
            System.out.println("线程中断状态；"+Thread.currentThread().isInterrupted());
            while(!Thread.currentThread().isInterrupted()){}
            System.out.println("子线程退出来while循环");
        });
        thread.start();
        //保证子线程顺利执行
        Thread.sleep(500);
        System.out.println("在主线程中中断子线程");
        thread.interrupt();
        System.out.println("主线程名称："+ Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {
//        setNameByMethod();
//        setNameByThread();
//        threadGroup();
//        threadSleep();
//        interruptSleepThread();
//        interruptRunningThread();
    }

}
