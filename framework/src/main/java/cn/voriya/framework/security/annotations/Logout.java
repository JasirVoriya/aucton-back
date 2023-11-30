package cn.voriya.framework.security.annotations;


import cn.voriya.framework.security.enums.UserEnums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于修饰用户注销接口方法，完成用户退出登录操作
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Logout {
    /**
     * 用户的登录身份
     * @return 用户身份
     */
    UserEnums[] role() default {UserEnums.USER};
}
