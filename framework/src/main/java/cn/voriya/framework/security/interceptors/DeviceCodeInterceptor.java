package cn.voriya.framework.security.interceptors;

import cn.voriya.framework.security.context.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Aspect
@Configuration
@Slf4j
public class DeviceCodeInterceptor {
    /**
     * 拦截所有Controller和RestController
     */
    @Before("@within(org.springframework.stereotype.Controller)||@within(org.springframework.web.bind.annotation.RestController)")
    public void interceptor() {
        //检查是否有设备码，如果没有则抛出异常
//        UserContext.getCurrentUserUUID();
    }
}
