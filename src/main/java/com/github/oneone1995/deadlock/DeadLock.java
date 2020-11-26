package com.github.oneone1995.deadlock;

/**
 * 死锁的例子
 */
public class DeadLock {
    public static void main(String[] args) {
        Object lockA = new Object();
        Object lockB = new Object();

        Thread tA = new Thread(() -> {
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + "获得LockA");
                try {
                    //sleep 1s 期间 tB线程获得lockB
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "尝试获得lockB");
                    synchronized (lockB) {
                        System.out.println(Thread.currentThread().getName() + "获得LockB");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread tB = new Thread(() -> {
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "获得LockB");
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "尝试获得LockA");
                    synchronized (lockA) {
                        System.out.println(Thread.currentThread().getName() + "获得LockA");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //假设tA线程先运行，获得lockA后，sleep 1s 期间 tB线程获得lockB，所以tA线程无法释放lockA
        //又因为tA线程始终没有释放lockA，导致tB线程无法获得lockA
        //tA tB两个线程相互持有对方需要的锁不释放，发生死锁
        tA.start();
        tB.start();
        /*
        打印结果为:
        Thread-0获得LockA
        Thread-1获得LockB
        Thread-0尝试获得lockB
        Thread-1尝试获得LockA
         */
    }
}
