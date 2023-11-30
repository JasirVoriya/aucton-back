package cn.voriya.framework.thread;

import cn.voriya.framework.properties.ThreadProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 多线程配置
 *
 *
 * @version v1.0
 *
 */
@Configuration
@EnableAsync
public class ThreadConfig implements AsyncConfigurer {


    private final ThreadProperties threadProperties;

    public ThreadConfig(ThreadProperties threadProperties) {
        this.threadProperties = threadProperties;
    }


    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//       核心线程数，默认为5
        executor.setCorePoolSize(threadProperties.getCorePoolSize());
//       最大线程数，默认为10
        executor.setMaxPoolSize(threadProperties.getMaxPoolSize());
//     队列最大长度，一般需要设置值为足够大
        executor.setQueueCapacity(threadProperties.getQueueCapacity());
//       线程池维护线程所允许的空闲时间，默认为60s
        executor.setKeepAliveSeconds(threadProperties.getKeepAliveSeconds());
//       允许超时关闭
        executor.setAllowCoreThreadTimeOut(threadProperties.getAllowCoreThreadTimeOut());
        executor.initialize();
        return executor;
    }
}
