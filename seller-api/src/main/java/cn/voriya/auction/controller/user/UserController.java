package cn.voriya.auction.controller.user;

import cn.voriya.auction.entity.dos.User;
import cn.voriya.auction.service.IUserService;
import cn.voriya.framework.entity.vo.ResultMessage;
import cn.voriya.framework.utils.ResultUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-02
 */
@RestController
@RequestMapping("user")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 分页获取所有用户
     */
    @GetMapping("all")
    public ResultMessage<Page<User>> getAllUser(Integer page, Integer size) {
        final Page<User> userPage = userService.getBaseMapper().selectPage(new Page<>(page, size), null);
        return ResultUtil.data(userPage);

    }
}
