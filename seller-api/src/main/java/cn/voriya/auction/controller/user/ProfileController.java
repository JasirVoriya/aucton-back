package cn.voriya.auction.controller.user;

import cn.voriya.auction.entity.vos.UserVO;
import cn.voriya.auction.service.IUserService;
import cn.voriya.framework.entity.vo.ResultMessage;
import cn.voriya.framework.security.annotations.Login;
import cn.voriya.framework.security.context.UserContext;
import cn.voriya.framework.security.enums.UserEnums;
import cn.voriya.framework.utils.ResultUtil;
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
    public ResultMessage<UserVO> info() {
        final Long id = UserContext.getCurrentUser().getId();
        final UserVO userVO = UserVO.valueOf(userService.getById(id));
        return ResultUtil.data(userVO);
    }
}
