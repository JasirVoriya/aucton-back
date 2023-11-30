package cn.voriya.framework.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * OOS配置参数
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wrx.oss")
public class OSSProperties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
