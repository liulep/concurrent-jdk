## JUC(Java.util.concurrent)

### 1.线程的基本操作

#### 1.1 创建线程

Java的创建方式主要包含继承Thread类、实现Runnable接口、实现Callable接口、FutureTask配合Thread和使用线程池5种

##### 1.1.1 继承Thread类

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

##### 1.1.2 实现Runnable接口

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

##### 1.1.3 实现Callable接口

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

##### 1.1.4 FutureTask配合Thread

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

##### 1.1.5 使用线程池

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

##### 1.1.6 通过ThreadPoolExecutor类创建线程

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