package com.github.oneone1995.scriptengine;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.InputStream;

/**
 * 实验，启动main方法。运行过程中修改out目录下的groovy-script-test.groovy文件内容
 * 同时用Arthas工具观察更新后原来的类是否会卸载
 */
public class GroovyScriptEngineDemo {
    public static void main(String[] args) throws Exception {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine groovy = scriptEngineManager.getEngineByName("groovy");
        while (true) {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("groovy-script-test.groovy");
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            String script = new String(b);
            groovy.eval(script);
            String result = (String) ((Invocable) groovy).invokeFunction("sayHello");
            System.out.println(result);

            /*
            观察得:
                1. groovy脚本会以脚本内容作为key缓存脚本对应的class对象，不需要每次都编译以及类加载
                2. 运行时修改脚本会因为key发生改变而缓存失效会重新触发类加载以及动态编译，并且原来的类并不会被卸载
             */
            //Thread.sleep(1000);
            System.gc();
            System.gc();
        }

    }
}
