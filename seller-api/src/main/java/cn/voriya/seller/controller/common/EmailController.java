package cn.voriya.seller.controller.common;

import cn.voriya.framework.email.EmailService;
import cn.voriya.framework.entity.vo.ResultMessage;
import cn.voriya.framework.security.enums.VerificationEnums;
import cn.voriya.framework.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
public class EmailController {
    @Autowired
    private EmailService emailService;
    @Value("${spring.mail.username}")
    private String from;
    @GetMapping("test/{email}")
    public ResultMessage<String> send(@PathVariable String email){
        emailService.sendCode(email,"验证码", VerificationEnums.LOGIN);
        return ResultUtil.data(from);
    }
}
