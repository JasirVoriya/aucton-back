package cn.voriya.auction.controller.user;

import cn.voriya.auction.service.IUserService;
import cn.voriya.framework.security.token.Token;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("passport")
public class PassportController {

    private final IUserService userService;

    public PassportController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 密码登录
     *
     * @param account  用户名或者邮箱
     * @param password 密码
     * @return token
     */
    @PostMapping("login/password")
    public Token passwordLogin(String account, String password) {
        return userService.passwordLogin(account,password);
    }

    /**
     * 验证码登录/注册
     *
     * @param email 邮箱
     * @param code  验证码
     * @return token
     */
    @PostMapping("login/code")
    public Token codeLogin(String email, String code) {
        return userService.codeLogin(email,code);
    }
}
