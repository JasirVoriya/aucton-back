package cn.voriya.auction.controller.user;

import cn.voriya.auction.entity.vos.UserVO;
import cn.voriya.auction.service.IUserService;
import cn.voriya.framework.security.annotations.Login;
import cn.voriya.framework.security.context.UserContext;
import cn.voriya.framework.security.enums.UserEnums;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("profile")
public class ProfileController {
    private final IUserService userService;

    public ProfileController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("info")
    @Login(role = {UserEnums.USER, UserEnums.MANAGER})
    public UserVO info() {
        final Long id = UserContext.getCurrentUser().getId();
        return UserVO.valueOf(userService.getById(id));
    }
}
