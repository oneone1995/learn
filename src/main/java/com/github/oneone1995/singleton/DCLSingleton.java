package com.github.oneone1995.singleton;

/**
 * dcl单例
 */
public class DCLSingleton {
    /**
     * volatile是必须的，因为new关键字也不是原子指令...如果不加volatile此处会存在指令重排序，步骤2跑到步骤3下面的话拿到的就是初始化不完整的对象
     * </code>
     * 1. 给 instance 分配内存
     * 2. 调用 DCLSingleton 的构造函数来初始化成员变量
     * 3. 将instance对象指向分配的内存空间

     */
    private static volatile DCLSingleton singleton;

    private DCLSingleton() {

    };

    public static DCLSingleton getInstance() {
        if (singleton == null) {
            synchronized (DCLSingleton.class) {
                if (singleton == null) {
                    singleton = new DCLSingleton();
                }
            }
        }
        return singleton;
    }
}
