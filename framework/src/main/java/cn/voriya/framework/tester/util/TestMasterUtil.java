package cn.voriya.framework.tester.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;


/**
 * 测试工具类
 */
public class TestMasterUtil {

    private static Logger log = LoggerFactory.getLogger(TestMasterUtil.class);

    private static final String LOG_TIME = "| Time(ms): %s |";
    private static final String LOG_MEMORY = "| Use Memory(B):%s | Max Memory(MB):%s | Total Memory(MB):%s | Free Memory(MB):%s |";
    private static final String threadLog = "| Thread%s:%s | State:%s | Priority:%s |";
    public static Object logTime(Supplier test){
        long startTime = System.currentTimeMillis();
        Object res = test.get();
        long endTime = System.currentTimeMillis();
        log.info("{}",String.format(LOG_TIME,endTime - startTime));
        return res;
    }

    public static Object logMemory(Supplier test,boolean isGC){
        int MB = 1048576;
        long freeMemory =  Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory()/MB;
        long maxMemory = Runtime.getRuntime().maxMemory();

        Object res = test.get();
        long currentFreeMemory = Runtime.getRuntime().freeMemory();

        if (isGC) {
            Runtime.getRuntime().gc();
        }
        log.info(String.format(LOG_MEMORY,freeMemory-currentFreeMemory,maxMemory/MB,totalMemory,currentFreeMemory/MB));

        return res;
    }

    public static Object logThread(Supplier test){
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        int total = Thread.activeCount();
        Thread[] threads = new Thread[total];
        threadGroup.enumerate(threads);

        Object res = test.get();

        String msg = "--------Thread List--------\n%s\n---------------------------";
        StringBuilder builder = new StringBuilder("");
        for (Thread t:threads){
            builder.append(String.format(threadLog,t.getId(),t.getName(),t.getState(),t.getPriority()));
        }
        log.info("{}",String.format(msg,builder.toString()));
        return res;
    }
}
