package cn.voriya.framework.controller;

import cn.voriya.framework.entity.enums.ResultCode;
import cn.voriya.framework.entity.vo.ResultMessage;
import cn.voriya.framework.utils.ResultUtil;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义错误控制器
 */
@RestController
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public ResultMessage<Object> handleError() {
        return ResultUtil.error(ResultCode.NOT_FOUND);
    }
}
