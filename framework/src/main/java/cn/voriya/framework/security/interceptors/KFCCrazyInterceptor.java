package cn.voriya.framework.security.interceptors;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

@Aspect
@Configuration
@Slf4j
public class KFCCrazyInterceptor {
//    @Before("@within(org.springframework.stereotype.Controller)||@within(org.springframework.web.bind.annotation.RestController)")
    public void interceptor() {
        LocalDateTime now = LocalDateTime.now();
        int day = now.getDayOfWeek().get(ChronoField.DAY_OF_WEEK);
        if (day == 4) {
            int i = RandomUtil.randomInt(1, 5);
            if (i == 3) {
                throw new IllegalArgumentException("KFC Crazy Thursday need $50");
            }
        }
    }
}
