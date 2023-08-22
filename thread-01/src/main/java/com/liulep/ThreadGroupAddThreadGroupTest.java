package com.liulep;

/**
 * 向现有的线程组当中添加线程组
 */
public class ThreadGroupAddThreadGroupTest {

    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("liulep-thread-group");
        System.out.println("threadGroup线程组中的活跃线程组数量为"+ threadGroup.activeGroupCount());
        ThreadGroup threadGroup1 = new ThreadGroup(threadGroup, "liuleo-sub-thread-group");
        System.out.println("threadGroup线程组中的活跃线程组数量为"+ threadGroup.activeGroupCount());
    }

}
