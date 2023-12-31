package cn.voriya.auction.service;

import cn.voriya.auction.entity.dos.User;
import cn.voriya.auction.entity.vos.UserVO;
import cn.voriya.framework.security.token.Token;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-02
 */
public interface IUserService extends IService<User> {

    Token passwordLogin(String account, String password);

    Token codeLogin(String email, String code);

    void updatePassword(String email, String code, String password);

    void updateEmail(String email, String code);

    void updateUsername(String username);
}
