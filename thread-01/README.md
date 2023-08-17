

## 线程的基本操作

### 1.1 创建线程

Java的创建方式主要包含继承Thread类、实现Runnable接口、实现Callable接口、FutureTask配合Thread和使用线程池5种

#### 1.1.1 继承Thread类

```java
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
```

> 弊端：由于Java是单继承的，不支持多继承，在实际项目中，不推荐直接使用继承Thread类的方式创建线程。

#### 1.1.2 实现Runnable接口

该方式可以通过创建匿名内部类、通过Lambda表达式和实现Runnable实现类三种方式实现。

通过匿名内部类的形式

```java
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

    public static void main(String[] args) {
        new RunnableTest().createThreadByRunnableAnonymousClass().start();
        System.out.println("主线程名称 =>>>>"+Thread.currentThread().getName());
    }
}
```

通过lambda表达式的形式

```java
/**
 * Runnable实现类
 */
public class RunnableTest {

    /**
     * 通过lambda表达式创建线程
     */
    public Thread createThreadByRunnableLambda(){
        return new Thread(() -> {
            System.out.println("子线程名称 =>>>>"+ Thread.currentThread().getName());
        });
    }

    public static void main(String[] args) {
        new RunnableTest().createThreadByRunnableLambda().start();
        System.out.println("主线程名称 =>>>>"+Thread.currentThread().getName());
    }
}
```

通过Runnable实现类的方式

```java
/**
 * Runnable实现类
 */
public class RunnableTest {

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
        Thread thread = new Thread(new MyRunnableTask());
        thread.start();
        System.out.println("主线程名称 =>>>>"+Thread.currentThread().getName());
    }
}
```

#### 1.1.3 实现Callable接口

不同与Runnable，Callable接口能够返回结果数据

该方式可以通过创建匿名内部类、通过Lambda表达式和实现Runnable实现类三种方式实现。

```java
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
```

#### 1.1.4 FutureTask配合Thread

FutureTask类实现了Runnable接口和Callable接口，所以可以直接跟Thread配合创建线程。

```java
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
```

#### 1.1.5 使用线程池

通过以上方式创建的线程，在执行完任务后就会被销毁，这些线程资源和线程实例都不能得到复用，线程的创建和销毁是非常消耗性能的，所以我们需要通过线程池技术创建线程。

线程池创建线程可以通过Executors工具类和ThreadPoolExecutor类创建线程

```java
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
```

线程池中的ExecutorService对象的submit()方法存在返回值，而execute()方法没有返回值。

#### 1.1.6 通过ThreadPoolExecutor类创建线程

《阿里巴巴java开发手册》中不建议使用Executors工具类创建线程，因为存在资源耗尽的分享。

```java
/**
 * 通过ThreadPoolExecutor类来创建线程
 */
public class ThreadPoolExecutorTest {

    public static ThreadPoolExecutor threadPool;

    static {
        threadPool = new ThreadPoolExecutor(3, 3, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));
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
```

### 1.2 线程的基本操作

#### 1.2.1 线程设置

```java
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

    public static void main(String[] args) {
        setNameByMethod();
        setNameByThread();
        threadGroup();
    }
}
```

#### 1.2.2 线程操作

> 启动线程
>
> ```java
> public synchronized void start()
> ```
>
> 线程休眠
>
> ```java
> public static native void sleep(long millis) throws InterruptedException;
> public static void sleep(long millis, int nanos) throws InterruptedException;
> 
>    //线程睡眠
>     private static void threadSleep(){
>         new Thread(() -> {
>             System.out.println("当前时间为:"+ new Date());
>             System.out.println("子线程名称："+ Thread.currentThread().getName());
>             try {
>                 Thread.sleep(2000);
>             }catch (InterruptedException ex){
>                 ex.printStackTrace();
>             }
>             System.out.println("当前时间为:"+ new Date());
>         }).start();
>         System.out.println("主线程名称："+Thread.currentThread().getName());
>     }
> ```
>
> 中断线程
>
> ```t x t
> * 线程中断interrupt()方法
> * （1）当线程被Object.wait()、Thread.join()和Thread.sleep()方法阻塞时，其他线程调用了中断线程的方法则线程会抛出异常，并会清空线程的中断标志,
> * （2）如果线程在运行中被调用了interrupt()方法，则当前线程的中断标志会被设置为true，此时，需要在中断的线程中通过Thread类的isInterrputd()方法来检测当前方法是否被中断
> ```
>
> ```java
> public void interrupt();
> ```
>
> ```java
> // 中断睡眠中的线程
> public static void interruptSleepThread() throws InterruptedException {
>     Thread thread = new Thread(() -> {
>         System.out.println("子线程名称：" + Thread.currentThread().getName());
>         try {
>             Thread.sleep(5000);
>         } catch (InterruptedException ex) {
>             //中断休眠中的线程会抛出异常，并清空中断标记
>             System.out.println("中断休眠中的线程会抛出异常，并清空中断标记");
>             Thread.currentThread().interrupt();
>         }
>     });
>     thread.start();
>     //保证子线程顺利执行
>     Thread.sleep(500);
>     System.out.println("在主线程中中断子线程");
>     thread.interrupt();
>     System.out.println("主线程名称："+ Thread.currentThread().getName());
> }
> ```
>
> ```java
> //中断正在运行的线程
> public static void interruptRunningThread() throws InterruptedException {
>     Thread thread = new Thread(() -> {
>         System.out.println("子线程名称：" + Thread.currentThread().getName());
>         while(!Thread.currentThread().isInterrupted()){}
>         System.out.println("子线程退出来while循环");
>     });
>     thread.start();
>     //保证子线程顺利执行
>     Thread.sleep(500);
>     System.out.println("在主线程中中断子线程");
>     thread.interrupt();
>     System.out.println("主线程名称："+ Thread.currentThread().getName());
> }
> ```
>
> 等待与通知
>
> ```
> 等待与通知操作可以调用Object对象的wait(),notify()或notifyAll()方法实现，前提是获取到synchornized对象锁。
> ```
>
> ```java
> /**
>  * 线程等待与通知
>  */
> public class ThreadWaitNotifyTest {
> 
>     public static void main(String[] args) throws InterruptedException {
>         final Object obj = new Object();
>         Thread thread = new Thread(() -> {
>             System.out.println("子线程名称:" + Thread.currentThread().getName());
>             System.out.println("子线程开始等待");
>             synchronized (obj) {
>                 try {
>                     obj.wait();
>                 } catch (InterruptedException ex) {
>                     ex.printStackTrace();
>                 }
>             }
>             System.out.println("子线程被唤醒");
>         });
>         thread.start();
>         //保证子线程先被启动
>         Thread.sleep(500);
>         System.out.println("主线程开始通知子线程");
>         synchronized (obj){
>             obj.notify();
>         }
>         System.out.println("主线程名称:" + Thread.currentThread().getName());
>     }
> }
> ```
>
> 挂起与执行
>
> Thread类提供了suspend()和resume()方法，调用suspend()方法会将线程挂起，调用resume()方法会使线程继续执行，不过这两个方法已经被废弃，不推荐使用
>
> ```java
> /**
>  *  线程挂起与执行测试
>  */
> public class ThreadSuspendAndResumeTest {
> 
>     public static void main(String[] args) throws InterruptedException {
>         final Object obj = new Object();
>         Thread thread = new Thread(() -> {
>             System.out.println("子线程名称：" + Thread.currentThread().getName());
>             synchronized (obj) {
>                 System.out.println("子线程挂起");
>                 Thread.currentThread().suspend();
>             }
>             System.out.println("子线程被唤醒");
>         });
>         thread.start();
>         //保证子线程启动
>         Thread.sleep(500);
>         System.out.println("主线程通知子线程继续执行");
>         thread.resume();
>         System.out.println("主线程名称：" + Thread.currentThread().getName());
>     }
> }
> ```
>
> 等待通知与谦让
>
> Thread类中的join()方法会等待当前线程结束，yield()方法是一个静态本地方法，调用后会将当前线程所占用的cpu资源让给其他线程。后续也会跟其余线程一起抢占cpu资源，如果当前线程执行的是低级别的任务就可以将本身所占用的cpu资源让出去，以便高级别的任务尽快执行完成。
>
> ```java
> /**
>  * 线程等待结果与谦让
>  */
> public class ThreadJoinTest {
> 
>     private static int sum = 0;
> 
>     public static void main(String[] args) throws InterruptedException {
>         Thread thread = new Thread(() -> {
>             System.out.println("子线程名称：" + Thread.currentThread().getName());
>             IntStream.range(0, 1000).forEach(i -> {
>                 sum += i;
>             });
>         });
>         thread.start();
>         //主线程将会等待子线程解锁
>         thread.join();
>         System.out.println("子线程计算结果为:"+sum);
>     }
> }
> ```
>
> 守护线程
>
> ```java
> public final void setDaemon(boolean on);
> // 设置为true设置为守护线程，false非守护线程
> ```
>
> 终止线程
>
> ```java
> /**
>  * 线程终止操作
>  */
> public class ThreadStopTest {
> 
>     public static void main(String[] args) throws InterruptedException {
>         Thread thread = new Thread(() -> {
>             System.out.println("子线程开始");
>             while (true) {
>             }
>         });
>         System.out.println("启动子线程："+ new Date());
>         thread.start();
>         //保证子线程启动
>         Thread.sleep(5000);
>         System.out.println("强制退出子线程："+new Date());
>         thread.stop();
>     }
> }
> ```
>
> 不推荐使用

### 1.3 线程组

#### 1.3.1 线程关联线程组

线程和线程组之间的关联关系包括一级关联和多级关联

一级关联指的是一个线程组中只有线程，没有线程组

```java
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

    public static void main(String[] args) throws InterruptedException {
        //threadTestV1();
        threadGroupV2();
    }
}
```