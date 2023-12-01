package cn.voriya.framework.security.interceptors;

import cn.voriya.framework.security.AuthUser;
import cn.voriya.framework.security.annotations.Login;
import cn.voriya.framework.entity.enums.ResultCode;
import cn.voriya.framework.exception.ServiceException;
import cn.voriya.framework.security.context.UserContext;
import cn.voriya.framework.security.enums.UserEnums;
import cn.voriya.framework.cache.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;

@Aspect
@Configuration
@Slf4j
public class LoginInterceptor {
    private final RedisTemplate<String, String> template;

    public LoginInterceptor(RedisTemplate<String, String> template) {
        this.template = template;
    }

    @Before("@annotation(loginAnnotation)")
    public void interceptor(Login loginAnnotation) {
        boolean login = loginAnnotation.value();
        //判断是否需要登录
        if (login) {
            UserEnums[] userEnums = loginAnnotation.role();
            String uuid = UserContext.getCurrentUserUUID();
            AuthUser authUser = UserContext.getCurrentUser();
            if (authUser == null) throw new ServiceException(ResultCode.USER_NOT_LOGIN);
            String loginKey = RedisKeyUtil.loginKey(authUser);
            String cache_uuid = template.opsForValue().get(loginKey);
            //用户未登录
            if (cache_uuid == null) throw new ServiceException(ResultCode.USER_NOT_LOGIN);
            //判断用户身份类型
            if (Arrays.stream(userEnums).noneMatch(role -> role == authUser.getRole()))
                throw new ServiceException(ResultCode.USER_NOT_LOGIN);
            //用户账号被挤掉了
            if (!uuid.equals(cache_uuid)) throw new ServiceException(ResultCode.OTHER_PLACE_LOGIN);
        }
    }
}