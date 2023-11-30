package cn.voriya.framework.tester.testhandler;

import cn.voriya.framework.tester.util.TestMasterUtil;

import java.util.function.Supplier;

public class ThreadTester implements TestHandler {
    @Override
    public Object goTest(Supplier tester) {
        return TestMasterUtil.logThread(tester);
    }
}
