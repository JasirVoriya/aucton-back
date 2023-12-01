package cn.voriya.framework.security.context;


import cn.voriya.framework.entity.enums.ResultCode;
import cn.voriya.framework.exception.ServiceException;
import cn.voriya.framework.security.AuthUser;
import cn.voriya.framework.security.enums.SecurityEnum;
import cn.voriya.framework.security.token.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 用户上下文
 */
public class UserContext {

    /**
     * 根据request获取用户信息
     *
     * @return 授权用户
     */
    public static AuthUser getCurrentUser() {
        String accessToken = getCurrentUserToken();
        return TokenUtil.parseToken(accessToken);
    }

    public static String getCurrentUserToken() {
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            return request.getHeader(SecurityEnum.HEADER_TOKEN.getValue());
        }
        return null;
    }

    public static String getCurrentUserUUID() {
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String uuid = request.getHeader(SecurityEnum.UUID.getValue());
            if(!StringUtils.hasLength(uuid)) throw new ServiceException(ResultCode.UUID_NOT_FIND);
            return uuid;
        }
        throw new ServiceException(ResultCode.UUID_NOT_FIND);
    }
}
