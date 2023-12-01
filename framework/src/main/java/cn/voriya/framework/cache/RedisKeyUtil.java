package cn.voriya.framework.cache;


import cn.voriya.framework.security.AuthUser;
import cn.voriya.framework.security.enums.VerificationEnums;

/**
 * 缓存key生成工具类
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
    public static String emailCodeKey(VerificationEnums type,String email, String uuid) {
        return String.format("%s-%s-%s-%s", CachePrefix.EMAIL_CODE.getPrefix(), type.name(), uuid, email);
    }


    public static String loginKey(AuthUser authUser) {
        return String.format("%s-%s-%s", CachePrefix.LOGIN.getPrefix(), authUser.getRole().name(), authUser.getId());
    }
    public static String apiLimitKey(String ip, String uri) {
        return String.format("%s-%s-%s", CachePrefix.API_ACCESS_LIMIT.getPrefix(), ip, uri);
    }
}
