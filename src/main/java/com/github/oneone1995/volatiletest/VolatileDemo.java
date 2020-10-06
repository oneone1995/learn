package com.github.oneone1995.volatiletest;

import java.util.concurrent.CountDownLatch;

/**
 * 实验，volatile修饰的race变量仍然不能保证是原子操作
 * 原因在于，虽然volatile能保证线程可见性，但是实际执行过程为
 * 1. 线程1将i的值load取出来，放置到cpu缓存中
 * 2. 再将此值放置到寄存器A中，然后A中的值自增1（寄存器A中保存的是中间值，没有直接修改i，因此其他线程并不会获取到这个自增1的值）
 * 3. 如果在此时线程2也执行同样的操作，获取值i==10,自增1变为11，然后马上刷入主内存。
 * 此时由于线程2修改了i的值，实时的线程1中的i==10的值缓存失效，重新从主内存中读取，变为11。接下来线程1恢复。将自增过后的A寄存器值11赋值给cpu缓存i。
 * 这样就出现了线程安全问题。
 *
 * 作者：千帆
 * 链接：https://www.zhihu.com/question/329746124/answer/1205806238
 * 来源：知乎
 */
public class VolatileDemo {
    public static volatile int race;

    public static void increase() {
        race++;
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(20);

        for (int i = 0; i < 20; i++) {
           new Thread(() -> {
                for (int j = 0; j < 20000; j++) {
                    increase();
                }
                latch.countDown();
            }).start();
        }

        latch.await();
        System.out.println(race);
    }
}
