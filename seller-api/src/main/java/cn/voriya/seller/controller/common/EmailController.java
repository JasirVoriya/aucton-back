package cn.voriya.seller.controller.common;

import cn.voriya.framework.email.EmailService;
import cn.voriya.framework.entity.vo.ResultMessage;
import cn.voriya.framework.security.annotations.AccessLimit;
import cn.voriya.framework.security.enums.VerificationEnums;
import cn.voriya.framework.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @AccessLimit(seconds = 60, maxCount = 1)
    @GetMapping("login/{email}")
    public ResultMessage<Boolean> getLoginCode(@PathVariable String email){
        emailService.sendCode(email,"登录", VerificationEnums.LOGIN);
        return ResultUtil.success();
    }
}
