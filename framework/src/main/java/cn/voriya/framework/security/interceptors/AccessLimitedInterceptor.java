package cn.voriya.framework.security.interceptors;

import cn.voriya.framework.cache.RedisKeyUtil;
import cn.voriya.framework.entity.enums.ResultCode;
import cn.voriya.framework.exception.ServiceException;
import cn.voriya.framework.security.annotations.AccessLimit;
import cn.voriya.framework.utils.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Objects;

@Aspect
@Configuration
@Slf4j
public class AccessLimitedInterceptor {

    private final RedisTemplate<String,Integer> redisTemplate;

    private final DefaultRedisScript<Boolean> limitScript;

    public AccessLimitedInterceptor(RedisTemplate<String, Integer> redisTemplate, DefaultRedisScript<Boolean> limitScript) {
        this.redisTemplate = redisTemplate;
        this.limitScript = limitScript;
    }

    @Before("@annotation(accessLimit)")
    public void interceptor(AccessLimit accessLimit) {
        int seconds = accessLimit.seconds();
        int maxCount = accessLimit.maxCount();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = IpUtils.getIpAddress(request);
        String uri = request.getRequestURI();
        String key = RedisKeyUtil.apiLimitKey(ip, uri);
        //将key装入List

        try {
            Boolean access = redisTemplate.execute(limitScript, List.of(key), maxCount, seconds);
            if(Boolean.FALSE.equals(access)){
                throw new ServiceException(ResultCode.LIMIT_ERROR);
            }
        }
        //如果从redis中执行都值判定为空，则这里跳过
        catch (NullPointerException e) {
            throw new ServiceException(ResultCode.LIMIT_ERROR);
        }
    }
}
