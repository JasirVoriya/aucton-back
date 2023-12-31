package cn.voriya.auction.controller.user;

import cn.voriya.auction.service.IUserService;
import cn.voriya.framework.entity.vo.ResultMessage;
import cn.voriya.framework.security.annotations.Login;
import cn.voriya.framework.security.annotations.Logout;
import cn.voriya.framework.security.token.Token;
import cn.voriya.framework.utils.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
     * 退出登录
     */
    @PostMapping("logout")
    @Logout
    public ResultMessage<Boolean> logout() {
        return ResultUtil.success();
    }
    /**
     * 密码登录
     *
     * @param account  用户名或者邮箱
     * @param password 密码
     * @return token
     */
    @PostMapping("login/password")
    public ResultMessage<Token> passwordLogin(String account, String password) {
        final Token token = userService.passwordLogin(account, password);
        return ResultUtil.data(token);
    }

    /**
     * 验证码登录/注册
     *
     * @param email 邮箱
     * @param code  验证码
     * @return token
     */
    @PostMapping("login/code")
    public ResultMessage<Token> codeLogin(String email, String code) {
        final Token token = userService.codeLogin(email, code);
        return ResultUtil.data(token);
    }

    /**
     * 验证码修改密码
     */
    @PutMapping("password")
    public ResultMessage<Boolean> updatePassword(String email, String code, String password) {
        userService.updatePassword(email, code, password);
        return ResultUtil.success();
    }
    /**
     * 修改邮箱
     *
     * @param email 新邮箱
     * @param code  验证码
     */
    @PutMapping("email")
    @Login
    public ResultMessage<Boolean> updateEmail(String email, String code) {
        userService.updateEmail(email, code);
        return ResultUtil.success();
    }
    /**
     * 修改用户名
     */
    @PutMapping("username")
    @Login
    public ResultMessage<Boolean> updateUsername(String username) {
        userService.updateUsername(username);
        return ResultUtil.success();
    }
}
