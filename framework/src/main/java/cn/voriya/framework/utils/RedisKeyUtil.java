package cn.voriya.framework.utils;


import cn.voriya.framework.cache.CachePrefix;
import cn.voriya.framework.security.AuthUser;
import cn.voriya.framework.security.enums.VerificationEnums;

/**
 * @create 2022/4/13
 * @description 生成redis的key的工具类
 */
public class RedisKeyUtil {

    /**
     * 生成验证码缓存key
     *
     * @param email 邮箱
     * @param uuid  客户端uuid
     * @param type  验证类型
     * @return 验证码缓存key
     */
    public static String emailKey(String email, String uuid, VerificationEnums type) {
        return CachePrefix.EMAIL_CODE.getPrefix() + "-" + type.name() + "-" + uuid + email;
    }

    public static String loginKey(AuthUser authUser) {
        return CachePrefix.LOGIN.getPrefix() + "-" + authUser.getRole().name() + "-" + authUser.getId();
    }
}
