package com.github.oneone1995.volatiletest;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 实验，去掉jdk中AtomicInteger类的volatile修饰符观察是否会出现线程不安全问题
 * 原生JDK写法 private volatile int value;
 */
public class AtomicIntegerWithoutVolatileTest {

    /**
     * 干掉volatile修饰符
     */
    private int value;

    private static final long valueOffset;

    private static final Unsafe unsafe;

    static {
        try {
            //这里使用反射获得unsafe实例，也可以使用 java -Xbootclasspath/a: ${path}   // 其中path为调用Unsafe相关方法的类所在jar包路径
            Class klass = Unsafe.class;
            Field field = null;
            field = klass.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);


            //从AtomicInteger拷贝过来的
            valueOffset = unsafe.objectFieldOffset
                    (AtomicIntegerWithoutVolatileTest.class.getDeclaredField("value"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }


    public static void main(String[] args) throws Exception {
        AtomicIntegerWithoutVolatileTest atomicIntegerWithoutVolatile = new AtomicIntegerWithoutVolatileTest();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                atomicIntegerWithoutVolatile.incrementAndGet();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                atomicIntegerWithoutVolatile.incrementAndGet();
            }
        });

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                atomicIntegerWithoutVolatile.incrementAndGet();
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

        /*
          AtomicInteger的源码的时候看到value是用volatile修饰的，但实际上看CAS的底层实现，是用一条LOCK指令和一条cmpxchg指令来保证原子性的
          这里应该是用的缓存一致性协议保证(旧CPU的话是锁内存总线)。
          那这么看来实际上是不用加volatile修饰的。根据运行结果也可以知道，输出结果永远为300000
         */
        System.out.println(atomicIntegerWithoutVolatile.value);
    }

    public final int incrementAndGet() {
        return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
    }
}
