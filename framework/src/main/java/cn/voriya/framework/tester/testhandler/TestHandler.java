package cn.voriya.framework.tester.testhandler;

import java.util.function.Supplier;

@FunctionalInterface
public interface TestHandler<T> {
    T goTest(Supplier tester);
}
