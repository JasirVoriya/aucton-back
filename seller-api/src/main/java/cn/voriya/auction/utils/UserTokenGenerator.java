package cn.voriya.auction.utils;

import cn.voriya.auction.entity.dos.User;
import cn.voriya.framework.security.AuthUser;
import cn.voriya.framework.security.enums.UserEnums;
import cn.voriya.framework.security.token.Token;
import cn.voriya.framework.security.token.TokenService;
import cn.voriya.framework.security.token.base.AbstractTokenGenerate;
import org.springframework.stereotype.Service;

@Service
public class UserTokenGenerator extends AbstractTokenGenerate<User> {
    private final TokenService tokenService;

    public UserTokenGenerator(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Token createToken(User user, Boolean longTerm) {
        AuthUser authUser = new AuthUser(user.getUsername(), user.getId(), UserEnums.USER, longTerm);
        return tokenService.createToken(authUser);
    }

    @Override
    public Token refreshToken(String refreshToken) {
        return tokenService.refreshToken(refreshToken);
    }
}
