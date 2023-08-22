package com.liulep;

import java.util.stream.Stream;

/**
 * 获取线程组内的对象
 */
public class ThreadGroupQueryObjectTest {

    public static void main(String[] args) {
        ThreadGroup mainThreadGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup threadGroup = new ThreadGroup(mainThreadGroup, "threadGroup");
        ThreadGroup threadGroup1 = new ThreadGroup(mainThreadGroup, "threadGroup2");
        ThreadGroup subThreadGroup1 = new ThreadGroup(threadGroup, "subThreadGroup1");
        ThreadGroup subThreadGroup2 = new ThreadGroup(threadGroup, "subThreadGroup2");
        ThreadGroup[] threadGroups = new ThreadGroup[mainThreadGroup.activeGroupCount()];
        //递归获取
        mainThreadGroup.enumerate(threadGroups, true);
        Stream.of(threadGroups).forEach(tg -> {
            if(tg != null){
                System.out.println("递归获取的线程组 ===> "+ tg.getName());
            }
        });

        ThreadGroup[] threadGroups1 = new ThreadGroup[mainThreadGroup.activeGroupCount()];
        //非递归获取
        mainThreadGroup.enumerate(threadGroups1, false);
        Stream.of(threadGroups1).forEach(tg -> {
            if(tg != null){
                System.out.println("非递归获取的线程组 ===> "+ tg.getName());
            }
        });

    }
}
