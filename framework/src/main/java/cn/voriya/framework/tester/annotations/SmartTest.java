package cn.voriya.framework.tester.annotations;

import cn.voriya.framework.tester.testhandler.TimeTester;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * author:Genius
 * 标记在方法上，会为该方法进行方法监视，查看其时间，内存，线程，流量等等，默认采用TimeTester方法
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SmartTest {

    Class[] testHandler() default TimeTester.class;
}
