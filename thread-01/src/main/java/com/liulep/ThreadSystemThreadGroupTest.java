package com.liulep;

//顶级线程组
public class ThreadSystemThreadGroupTest {

    public static void main(String[] args) {
        //当前线程组
        System.out.println(Thread.currentThread().getThreadGroup().getName());
        //当前线程组的父线程组
        System.out.println(Thread.currentThread().getThreadGroup().getParent().getName());
        //当前线程组的爷爷线程组
        System.out.println(Thread.currentThread().getThreadGroup().getParent().getParent().getName());
    }
}
