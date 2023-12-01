package cn.voriya.framework.email;

import cn.voriya.framework.cache.RedisKeyUtil;
import cn.voriya.framework.entity.enums.ResultCode;
import cn.voriya.framework.exception.ServiceException;
import cn.voriya.framework.security.context.UserContext;
import cn.voriya.framework.security.enums.VerificationEnums;
import cn.voriya.framework.utils.CommonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * 只有在客户端获取验证码的时候，客户段需要带上VerificationEnums，
 * 服务器核对验证码的时候，客户端不需要带上VerificationEnums，VerificationEnums由controller层对应的接口提供，
 * 客户端只需要带上验证码和其他信息。
 */
@Service
@Slf4j
public class EmailService {

    private final RedisTemplate<String, String> template;
    private final EmailUtil emailUtil;
    public EmailService(RedisTemplate<String, String> template, EmailUtil emailUtil) {
        this.template = template;
        this.emailUtil = emailUtil;
    }

    /**
     * 生成html内容
     *
     * @param code    邮箱验证码
     * @param context 邮箱内容描述
     */
    @SneakyThrows
    private String makeHtmlContext(String code, String context) {
        File file = new File(Objects.requireNonNull(EmailService.class.getClassLoader().getResource("email-template.html")).getPath());
        InputStream stream = EmailService.class.getClassLoader().getResourceAsStream("email-template.html");
        final Document document = Jsoup.parse(stream, "utf-8", file.getAbsolutePath());
        final Element codeElement = document.getElementById("code");
        final Element contextElement = document.getElementById("context");

        if (codeElement == null || contextElement == null) {
            log.error("html文件损坏");
            return null;

        }
//            填写验证码
        codeElement.text(code);
//            填写邮箱内容
        contextElement.text(context);
        return document.toString();
    }
    /**
     * 对service层暴露的发送邮箱验证码方法
     *
     * @param email             目标邮箱
     * @param title             标题
     * @param verificationEnums 此次邮箱的验证类型
     */
    public void sendCode(String email, String title, VerificationEnums verificationEnums) {

        String uuid = UserContext.getCurrentUserUUID();
        //验证码
        String code = CommonUtil.getRandomNum();
        String context = String.valueOf(verificationEnums);
        //生成html内容
        String htmlContext = makeHtmlContext(code, context);
        if (htmlContext == null) throw new ServiceException(ResultCode.EMAIL_SEND_ERROR);
        //发送邮件
        emailUtil.sendHtmlEmailAsync(email, title, htmlContext);
        //缓存中写入要验证的信息
        template.opsForValue().set(RedisKeyUtil.emailCodeKey(verificationEnums, email,uuid), code, 5L, TimeUnit.MINUTES);
    }


    /**
     * 对service层暴露的校验验证码的方法
     *
     * @param email             邮箱
     * @param verificationEnums 验证码类型
     * @param code              验证码
     * @return 校验结果
     */
    public boolean verifyCode(String email, VerificationEnums verificationEnums, String code) {
        String uuid = UserContext.getCurrentUserUUID();
        //从缓存中获取验证码
        String cacheCode = template.opsForValue().get(RedisKeyUtil.emailCodeKey(verificationEnums, uuid, email));
        if (code.equals(cacheCode)) {
            //校验之后，删除
            template.delete(RedisKeyUtil.emailCodeKey(verificationEnums, uuid, email));
            return true;
        } else {
            return false;
        }
    }
}
