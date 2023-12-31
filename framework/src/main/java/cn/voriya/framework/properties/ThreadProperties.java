package cn.voriya.framework.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "wrx.thread")
public class ThreadProperties {


    /**
     * 核心线程数
     */
    private Integer corePoolSize = 10;

    /**
     * 最大线程数
     */
    private Integer maxPoolSize = 50;

    /**
     * 队列最大长度
     */
    private Integer queueCapacity = Integer.MAX_VALUE;

    /**
     * 允许超时关闭
     */
    private Boolean allowCoreThreadTimeOut = false;

    /**
     * 保持存活时间
     */
    private Integer keepAliveSeconds = 60;


}
