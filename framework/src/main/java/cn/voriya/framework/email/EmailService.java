package cn.voriya.framework.email;

import cn.voriya.framework.cache.CachePrefix;
import cn.voriya.framework.cache.impl.RedisCache;
import cn.voriya.framework.entity.enums.ResultCode;
import cn.voriya.framework.exception.ServiceException;
import cn.voriya.framework.security.context.UserContext;
import cn.voriya.framework.security.enums.VerificationEnums;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

    private final RedisCache cache;
    private final EmailUtil emailUtil;
    public EmailService(RedisCache cache, EmailUtil emailUtil) {
        this.cache = cache;
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
        if (uuid == null) throw new ServiceException(ResultCode.UUID_NOT_FIND);
        //验证码
        String code = String.valueOf(Math.abs((new Random()).nextInt() % (int) 1e5));
        String context = String.valueOf(verificationEnums);
        //生成html内容
        String htmlContext = makeHtmlContext(code, context);
        if (htmlContext == null) throw new ServiceException(ResultCode.EMAIL_SEND_ERROR);
        //发送邮件
        emailUtil.sendHtmlEmailAsync(email, title, htmlContext);
        //缓存中写入要验证的信息
        cache.put(cacheKey(verificationEnums, uuid, email), code, 5L, TimeUnit.MINUTES);
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
        if (uuid == null) throw new ServiceException(ResultCode.UUID_NOT_FIND);
        Object val = cache.get(cacheKey(verificationEnums, uuid, email));
        if (code.equals(val)) {
            //校验之后，删除
            cache.remove(cacheKey(verificationEnums, uuid, email));
            return true;
        } else {
            return false;
        }

    }

    /**
     * 生成缓存key，邮箱验证码的缓存key都统一使用该方法生成
     *
     * @param verificationEnums 验证码的使用场景，如用来找回密码，用来登录等等
     * @param uuid              客户端的uuid
     * @param email             邮箱
     * @return 生成的缓存key
     */
    private static String cacheKey(VerificationEnums verificationEnums, String uuid, String email) {
        return CachePrefix.EMAIL_CODE.getPrefix() + verificationEnums.name() + uuid + email;
    }
}
