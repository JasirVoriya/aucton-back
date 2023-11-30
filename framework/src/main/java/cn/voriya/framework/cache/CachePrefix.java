package cn.voriya.framework.cache;


import cn.voriya.framework.security.enums.UserEnums;

/**
 * 缓存前缀
 */
public enum CachePrefix {
    /**
     * nonce
     */
    NONCE,
    /**
     * 地区
     */
    REGION,
    /**
     * 系统设置
     */
    SETTINGS,
    /**
     * 邮箱验证码
     */
    EMAIL_CODE,
    /**
     * 接口访问限制
     */
    API_ACCESS_LIMIT,
    /**
     * 用户
     */
    MEMBER,
    LOGIN;

    public static String removePrefix(String str) {
        return str.substring(str.lastIndexOf("}") + 2);
    }

    /**
     * 通用获取缓存key值
     *
     * @return 缓存key值
     */
    public String getPrefix() {
        return "{" + this.name() + "}";
    }

    /**
     * 获取缓存key值 + 用户端
     * 例如：三端都有用户体系，需要分别登录，如果用户名一致，则redis中的权限可能会冲突出错
     *
     * @param user 角色
     * @return 缓存key值 + 用户端
     */
    public String getPrefix(UserEnums user) {
        return "{" + this.name() + "-" + user.name() + "}";
    }
}
