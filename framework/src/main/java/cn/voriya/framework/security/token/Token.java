package cn.voriya.framework.security.token;

import lombok.Data;

/**
 * Token 实体类
 *
 */
@Data
public class Token {
    /**
     * 访问token
     */
    private String accessToken;

    /**
     * 刷新token
     */
    private String refreshToken;

}
