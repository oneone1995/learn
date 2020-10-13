package com.github.oneone1995.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class ReflectUnsafe {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        //这里使用反射获得unsafe实例，也可以使用 java -Xbootclasspath/a: ${path}   // 其中path为调用Unsafe相关方法的类所在jar包路径
        Class klass = sun.misc.Unsafe.class;
        Field field = null;
        field = klass.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        System.out.println(unsafe);
    }
}
