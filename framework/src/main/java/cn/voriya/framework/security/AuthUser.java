package cn.voriya.framework.security;

import cn.voriya.framework.security.enums.UserEnums;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@NoArgsConstructor
public class AuthUser {
    /**
     * 用户名
     */
    private String username;

    /**
     * id
     */
    private Long id;

    /**
     * @see UserEnums
     * 角色
     */
    private UserEnums role;

    /**
     * 是否是长登录
     */
    private Boolean longTerm;

    public AuthUser(String username, Long id, UserEnums role, Boolean longTerm) {
        this.username = username;
        this.id = id;
        this.role = role;
        this.longTerm = longTerm;
    }
}
