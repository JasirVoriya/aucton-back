package cn.voriya.framework.security;
import cn.voriya.framework.security.enums.UserEnums;
import lombok.Data;

import java.io.Serializable;

/**
 *
 */
@Data
public class AuthUser implements Serializable {

    private static final long serialVersionUID = 582441893336003319L;

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
