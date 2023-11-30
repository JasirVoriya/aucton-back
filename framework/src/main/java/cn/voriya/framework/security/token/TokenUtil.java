package cn.voriya.framework.security.token;

import cn.voriya.framework.security.AuthUser;
import cn.voriya.framework.security.enums.SecurityEnum;
import cn.voriya.framework.utils.JsonUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;

public class TokenUtil {
    /**
     * 生成token
     *
     * @param authUser       鉴权用户
     * @param expirationTime 过期时间（分钟）
     * @return token字符串
     */
    public static String createToken(AuthUser authUser, Long expirationTime) {
        //JWT 生成
        return Jwts.builder()
                //jwt 私有声明
                .claim(SecurityEnum.USER_CONTEXT.getValue(), JsonUtil.toJson(authUser))
                //JWT的主体
                .setSubject(authUser.getUsername())
                //失效时间 当前时间+过期分钟
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 60 * 60 * 1000))
                //签名算法和密钥
                .signWith(SecretKeyUtil.generalKey())
                .compact();
    }


    /**
     * 从token中解析出鉴权用户，失败则返回NULL
     *
     * @param token token字符串
     * @return 鉴权用户 | NULL
     */
    public static AuthUser parseToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(SecretKeyUtil.generalKeyByDecoders())
                    .build()
                    .parseClaimsJws(token).getBody();

        } catch (Exception e) {
            //token 过期 认证失败等
            return null;
        }
        //获取存储在claims中的用户信息
        String authUserJson = claims.get(SecurityEnum.USER_CONTEXT.getValue()).toString();
        return JsonUtil.toObject(authUserJson, AuthUser.class);
    }

}
