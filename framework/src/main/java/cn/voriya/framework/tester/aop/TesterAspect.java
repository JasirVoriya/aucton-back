package cn.voriya.framework.tester.aop;

import cn.voriya.framework.tester.annotations.SmartTest;
import cn.voriya.framework.tester.testhandler.TestHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Supplier;

/**
 * author：Genius
 * Tester的aop，负责拦截SmartTest注解，对注解上的测试方法进行分析并嵌套
 * TODO 测试方法名未输出，输出方式应该美化
 */
@Component
@Aspect
public class TesterAspect {


    /**
     * 所有测试函数入口，可被嵌套调用
     * TODO 存在嵌套调用 重复的问题，还未找到解决方法捏
     *
     */
    class MotherTester implements TestHandler {

        private TestHandler sonTester;
        private TestHandler currentTester;

        @Override
        public Object goTest(Supplier tester) {
            return sonTester.goTest(()->{return currentTester.goTest(tester);});
        }


        private void addTester(TestHandler testHandler){

            if(sonTester==null) {
                currentTester = sonTester = testHandler;
            }else{
                currentTester.goTest(()->testHandler);
                currentTester = testHandler;
            }
        }


    }

    private TestHandler getTester(Class[] handlers){
        MotherTester motherTester = new MotherTester();

        Arrays.stream(handlers).forEach((handler)->{
            if(TestHandler.class.isAssignableFrom(handler)){
                try {
                    TestHandler tester =(TestHandler)handler.getDeclaredConstructor().newInstance();
                    motherTester.addTester(tester);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        return motherTester;

    }

    @Around("@annotation(cn.voriya.framework.tester.annotations.SmartTest)")
    private Object handlerTest(final ProceedingJoinPoint point)throws Throwable{

        Method targetMethod  = ((MethodSignature) point.getSignature()).getMethod();
        Class<?>[] parameterTypes = targetMethod.getParameterTypes();
        Method methodWithAnnotations = point.getTarget().getClass().getDeclaredMethod(point.getSignature().getName(), parameterTypes);
        SmartTest smartTest = methodWithAnnotations.getDeclaredAnnotation(SmartTest.class);

        Class[] handlers = smartTest.testHandler();

        Supplier supplier = ()->{
            try {
                return point.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return null;
        };

        MotherTester motherTester = (MotherTester) getTester(handlers);


        return motherTester.goTest(supplier);
    }
}


