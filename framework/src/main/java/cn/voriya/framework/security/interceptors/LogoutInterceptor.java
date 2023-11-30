package cn.voriya.framework.security.interceptors;


import cn.voriya.framework.cache.Cache;
import cn.voriya.framework.entity.enums.ResultCode;
import cn.voriya.framework.exception.ServiceException;
import cn.voriya.framework.security.AuthUser;
import cn.voriya.framework.security.annotations.Logout;
import cn.voriya.framework.security.context.UserContext;
import cn.voriya.framework.security.enums.UserEnums;
import cn.voriya.framework.utils.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Aspect
@Configuration
@Slf4j
public class LogoutInterceptor {
    @Autowired
    private Cache cache;

    @Before("@annotation(logout)")
    public void interceptor(Logout logout) {
        AuthUser authUser = UserContext.getCurrentUser();
        if (authUser == null) throw new ServiceException(ResultCode.USER_NOT_LOGIN);
        //判断用户身份类型
        UserEnums[] userEnums = logout.role();
        if (Arrays.stream(userEnums).noneMatch(role -> role == authUser.getRole()))
            throw new ServiceException(ResultCode.USER_NOT_LOGIN);
        String loginKey = RedisKeyUtil.loginKey(authUser);
        String uuid = (String) cache.get(loginKey);
        if (uuid == null) throw new ServiceException(ResultCode.USER_NOT_LOGIN);
        cache.remove(loginKey);
    }
}
