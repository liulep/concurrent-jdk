package com.liulep;

/**
 * 线程关联线程组
 */
public class ThreadGroupTest {

    //线程一级关联线程组
    public static void threadTestV1() throws InterruptedException {
        ThreadGroup threadGroup = new ThreadGroup("liulep-group");
        Thread threadA = new Thread(threadGroup, () -> {
            System.out.println("子线程名称：" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        Thread threadB = new Thread(threadGroup, () -> {
            System.out.println("子线程名称：" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        threadA.start();
        threadB.start();
        //保证子线程全都启动
        Thread.sleep(500);
        System.out.println("线程组中活跃的线程数量为"+ threadGroup.activeCount());
    }

    //线程多级关联线程组
    public static void threadGroupV2() throws InterruptedException {
        ThreadGroup threadGroup = new ThreadGroup("liulep-group");
        ThreadGroup subThreadGroup = new ThreadGroup(threadGroup, "liulep-sub-group");
        Thread threadA = new Thread(threadGroup, () -> {
            System.out.println("子线程名称：" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        Thread threadB = new Thread(subThreadGroup, () -> {
            System.out.println("子线程名称：" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        threadA.start();
        threadB.start();
        //保证子线程全都启动
        Thread.sleep(500);
        System.out.println("父线程组中活跃的线程数量为"+ threadGroup.activeCount());
        System.out.println("子线程组中活跃的线程数量为"+ subThreadGroup.activeCount());
    }

    //线程组自动归属
    public static void threadGroupV3() throws InterruptedException {
        ThreadGroup threadGroup = new ThreadGroup("liulep-group");
        ThreadGroup subThreadGroup = new ThreadGroup(threadGroup, "liulep-sub-group");
        Thread threadA = new Thread(threadGroup, () -> {
            System.out.println("子线程名称：" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        Thread threadB = new Thread(subThreadGroup, () -> {
            System.out.println("子线程名称：" + Thread.currentThread().getName());
            //创建一个新的线程组
            ThreadGroup threadGroup1 = new ThreadGroup("liulep-thread-group");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        threadA.start();
        threadB.start();
        //保证子线程全都启动
        Thread.sleep(500);
        System.out.println("父线程组中活跃的线程数量为"+ threadGroup.activeCount());
        System.out.println("子线程组中活跃的线程数量为"+ subThreadGroup.activeCount());
        System.out.println("父线程组中活跃的线程组数量为"+ threadGroup.activeGroupCount());
        System.out.println("子线程组中活跃的线程组数量为"+ subThreadGroup.activeGroupCount());
    }

    public static void main(String[] args) throws InterruptedException {
        //threadTestV1();
//        threadGroupV2();
        threadGroupV3();
    }


}
