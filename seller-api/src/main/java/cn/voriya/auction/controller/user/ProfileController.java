package cn.voriya.auction.controller.user;

import cn.voriya.auction.entity.dos.User;
import cn.voriya.auction.entity.vos.UserVO;
import cn.voriya.auction.service.IUserService;
import cn.voriya.framework.entity.vo.ResultMessage;
import cn.voriya.framework.security.annotations.Login;
import cn.voriya.framework.security.context.UserContext;
import cn.voriya.framework.security.enums.UserEnums;
import cn.voriya.framework.utils.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.web.bind.annotation.*;

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
    /**
     * 修改头像
     */
    @PutMapping ("info/avatar")
    @Login
    public ResultMessage<Boolean> updateAvatar(String avatar) {
        final Long id = UserContext.getCurrentUser().getId();
        final UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id).set("avatar",avatar);
        userService.update(wrapper);
        return ResultUtil.success();
    }
    /**
     * 修改性别
     */
    @PutMapping ("info/sex")
    public ResultMessage<Boolean> updateSex(Integer sex){
        final Long id = UserContext.getCurrentUser().getId();
        final UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id).set("sex",sex);
        userService.update(wrapper);
        return ResultUtil.success();
    }
}
