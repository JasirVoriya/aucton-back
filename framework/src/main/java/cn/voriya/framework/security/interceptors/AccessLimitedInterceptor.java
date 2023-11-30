package cn.voriya.framework.security.interceptors;


import cn.voriya.framework.cache.CachePrefix;
import cn.voriya.framework.security.annotations.AccessLimit;
import cn.voriya.framework.entity.enums.ResultCode;
import cn.voriya.framework.exception.ServiceException;
import cn.voriya.framework.utils.IpUtils;
import com.google.common.collect.ImmutableList;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.data.redis.core.*;

import java.util.Objects;

@Aspect
@Configuration
@Slf4j
public class AccessLimitedInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DefaultRedisScript<Boolean> limitScript;

    @Before("@annotation(accessLimit)")
    public void interceptor(AccessLimit accessLimit) {
        int seconds = accessLimit.seconds();
        int maxCount = accessLimit.maxCount();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = IpUtils.getIpAddress(request);
        String uri = request.getRequestURI();
        String key = getKey(ip, uri);
        ImmutableList<String> keys = ImmutableList.of(StringUtils.join(key));
        try {
            Boolean access = (Boolean) redisTemplate.execute(limitScript, keys, maxCount, seconds);
            if(Boolean.FALSE.equals(access)){
                throw new ServiceException(ResultCode.LIMIT_ERROR);
            }
        }
        //如果从redis中执行都值判定为空，则这里跳过
        catch (NullPointerException e) {
            throw new ServiceException(ResultCode.LIMIT_ERROR);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ResultCode.ERROR);
        }
    }

    private String getKey(String ip, String uri) {
        return CachePrefix.API_ACCESS_LIMIT + "_" + ip + "_" + uri;
    }
}
