package cn.voriya.framework.security.annotations;

import cn.voriya.framework.security.enums.UserEnums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 判断用户是否登录
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Login {
    boolean value() default false;

    /**
     * 用户的登录身份
     * @return
     */
    UserEnums[] role() default {UserEnums.USER};
}
