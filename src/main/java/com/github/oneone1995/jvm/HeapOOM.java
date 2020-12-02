package com.github.oneone1995.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * java堆内存溢出demo
 * 启动参数 -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 *
 * 通过启动参数-XX:+HeapDumpOnOutOfMemoryError可以在发生OOM时Dump出当前的内存堆转储快照
 * 当然还可以通过`jmap`、`kill -3`等方式拿到堆转储快照
 *
 * 出现java.lang.OutOfMemoryError: Java heap space。最关键的便是heap space，这种错误便是堆内存溢出抛的
 * 解决思路便是dump出当前的内存堆转储快照，进而用jhat或者其他堆转储快照分析工具分析是否出现了无法被gc的对象，往往可以通过引用路径便能定位。
 * 如果定位到的对象确实需要存活，那么则需要调大Xms与Xmx。否则则为程序bug或者待优化点。例如长时间持有对象、对象生命周期过长等问题
 */
public class HeapOOM {
    public static class OOMObject {

    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }

        /*
        java.lang.OutOfMemoryError: Java heap space
        Dumping heap to java_pid8876.hprof ...
        Heap dump file created [28426277 bytes in 0.079 secs]
        Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
            at java.util.Arrays.copyOf(Arrays.java:3210)
            at java.util.Arrays.copyOf(Arrays.java:3181)
            at java.util.ArrayList.grow(ArrayList.java:267)
            at java.util.ArrayList.ensureExplicitCapacity(ArrayList.java:241)
            at java.util.ArrayList.ensureCapacityInternal(ArrayList.java:233)
            at java.util.ArrayList.add(ArrayList.java:464)
            at com.github.oneone1995.jvm.HeapOOM.main(HeapOOM.java:17)
         */
    }
}