package com.github.oneone1995.spring;

import com.github.oneone1995.spring.component.A;
import com.github.oneone1995.spring.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanPostProcessorDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println(context.getBean(A.class));
        /*
        1. 第一个后置处理器 AbstractAutoProxyCreator
            父类是InstantiationAwareBeanPostProcessor
            方法是postProcessBeforeInstantiation()
            作用是做了一些判断(是否已经被代理过、是否不符合aspectj表达式(不符合的类当然不用代理)、是否本身就是一个通知类(是的话也不能被代理))
            同时该后置处理器可以通过自定义代理提前生成代理对象
            当然了，这里如果已经生成了代理对象，当然还需要执行postProcessAfterInstantiation()
        2. 第二个后置处理器 AutowiredAnnotationBeanPostProcessor
            父类是SmartInstantiationAwareBeanPostProcessor
            方法是determineCandidateConstructors()
            作用是推断构造方法用来实例化bean，主要是根据你有没有在构造方法上加@Autowired
        3. 第三个后置处理器 AutowiredAnnotationBeanPostProcessor与CommonAnnotationBeanPostProcessor
            父类：MergedBeanDefinitionPostProcessor
            方法：postProcessMergedBeanDefinition()
            作用：收集lifecycle callback方法，如加了@PostConstruct  @PreDestroy。以及收集待注入属性的信息，如属性或者方法。
            这里有一个平时很容易忽略的点，即@Autowired与@Resource的区别
            CommonAnnotationBeanPostProcessor负责找到@Resource的
            AutowiredAnnotationBeanPostProcessor负责找到@Autowired、@Value的
        4. 第四个后置处理器 AbstractAutoProxyCreator
            父类: SmartInstantiationAwareBeanPostProcessor
            方法: getEarlyBeanReference()
            作用: 此后置处理器被包裹在SingletonFactory中，主要用来解决循坏依赖问题中提前获得AOP代理对象
        5. 第五个后置处理器 InstantiationAwareBeanPostProcessor
            方法：postProcessAfterInstantiation()
            所有子类均返回true
            作用: 用来控制bean是否需要进行属性填充，在spring中的话所有子类均返回true，当然BeanPostProcessor本身就是扩展点，程序员可以手动控制
        6. 第六个后置处理器 AutowiredAnnotationBeanPostProcessor与CommonAnnotationBeanPostProcessor
            父类: InstantiationAwareBeanPostProcessor
            方法: postProcessPropertyValues()方法
            作用: 完成属性注入。
                以AutowiredAnnotationBeanPostProcessor举例，最终会调用DefaultListableBeanFactory.doResolveDependency方法进行依赖解析
                首先通过DefaultListableBeanFactory.findAutowireCandidates方法，该方法会先通过待注入的类型找到bean的名字.可能找到多个候选的Object
                然后在doResolveDependency方法中判断各种情况:
                    如果只找到1个候选object，直接返回
                    如果有大于1个候选object，先推断出属性名，再根据属性名在候选的objects匹配出一个object

                这个里面不仅包括了本身的功能，还处理了@Lazy的场景,如果带有@Laze则会生成一个代理对象
        7. 第七个后置处理器InitDestroyAnnotationBeanPostProcessor，ApplicationContextAwareProcessor
            子类:很多个。。
            方法: postProcessBeforeInitialization()
            作用: 执行各种Aware接口以及执行lifecycle callback @PostConstruct
        8. 第八个后置处理器 AbstractAdvisorAutoProxyCreator等等
            父类: BeanPostProcessor
            方法: 以AOP这个来举例是其比较经典，在其中完成了对@Aspectj注解类的查找，找到各种切点和通知并封装在对象中，然后判断当前类是否匹配各种规则，最后使用动态代理完成
         */
    }
}
