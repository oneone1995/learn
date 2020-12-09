package com.github.oneone1995.synthetic;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

public class SyntheticClassDemo {
    Inner inner = new Inner();

    class Inner {
        //构造方法为私有，但是在外部却能被访问
        private Inner() {

        }
    }

    public static void main(String[] args) {
        for (Constructor<?> constructor : Inner.class.getDeclaredConstructors()) {
            System.out.println(constructor.getName());
            for (Parameter parameter : constructor.getParameters()) {
                System.out.println("parameter: " + parameter.getType());
            }
            System.out.println("-------------------------------------------");
        }
        /*
        打印内部类的构造函数，内部类竟然产生了2个构造方法
        com.github.oneone1995.synthetic.SyntheticClassDemo$Inner
        parameter: class com.github.oneone1995.synthetic.SyntheticClassDemo
        -------------------------------------------
        com.github.oneone1995.synthetic.SyntheticClassDemo$Inner
        parameter: class com.github.oneone1995.synthetic.SyntheticClassDemo
        parameter: class com.github.oneone1995.synthetic.SyntheticClassDemo$1
        -------------------------------------------
         */
    }
}
