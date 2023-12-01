package cn.voriya.framework.security.token;


import cn.voriya.framework.entity.enums.ResultCode;
import cn.voriya.framework.exception.ServiceException;
import cn.voriya.framework.properties.JWTTokenProperties;
import cn.voriya.framework.security.AuthUser;
import cn.voriya.framework.security.context.UserContext;
import cn.voriya.framework.cache.RedisKeyUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * TokenService
 */
@Service
public class TokenService {
    private final JWTTokenProperties tokenProperties;
    private final RedisTemplate<String,String> template;

    public TokenService(JWTTokenProperties tokenProperties, RedisTemplate<String, String> template) {
        this.tokenProperties = tokenProperties;
        this.template = template;
    }

    /**
     * 用于用户登陆的时候创建token
     *
     * @param authUser 鉴权用户
     * @return token
     */
    public Token createToken(AuthUser authUser) {
        String uuid = UserContext.getCurrentUserUUID();
        if(!StringUtils.hasLength(uuid))throw new ServiceException(ResultCode.UUID_NOT_FIND);
        String loginKey = RedisKeyUtil.loginKey(authUser);
        //获取是否长期有效的token
        boolean longTerm = authUser.getLongTerm();
        //访问token
        String accessToken = TokenUtil.createToken(authUser, tokenProperties.getTokenExpireTime());
        //将用户的登录设备存入缓存
        template.opsForValue().set(loginKey, uuid, tokenProperties.getTokenExpireTime(), TimeUnit.HOURS);
        //刷新token生成策略：如果是长时间有效的token（用于app），则默认15天有效期刷新token。如果是普通用户登录，则刷新token为普通token2倍数
        Long expireTime = longTerm ? 15 * 24 * 60L : tokenProperties.getTokenExpireTime() * 2;
        String refreshToken = TokenUtil.createToken(authUser, expireTime);

        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        return token;
    }
    /**
     * 要是用户的刷新token还没有过期，就重新给用户生成新的token，否则抛出登录过期异常
     *
     * @param oldRefreshToken 刷新token
     * @return token
     */
    public Token refreshToken(String oldRefreshToken) {
        AuthUser authUser = TokenUtil.parseToken(oldRefreshToken);

        if (authUser == null) throw new ServiceException(ResultCode.USER_AUTH_EXPIRED);
        return createToken(authUser);
    }
}
