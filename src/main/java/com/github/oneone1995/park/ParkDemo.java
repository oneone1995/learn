package com.github.oneone1995.park;

import java.util.concurrent.locks.LockSupport;

public class ParkDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            long start = System.currentTimeMillis();
            System.out.println("t1 start");
            LockSupport.park();
            //t1 end在unpark之前不会执行，main线程sleep 5000ms 观察得time也为5000
            System.out.println("t1 end");
            System.out.println("time : " + (System.currentTimeMillis() - start));
        });

        t1.start();

        Thread.sleep(5 * 1000);
        LockSupport.unpark(t1);
    }
}
