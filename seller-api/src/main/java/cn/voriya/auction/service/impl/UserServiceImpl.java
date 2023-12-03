package cn.voriya.auction.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.voriya.auction.entity.dos.User;
import cn.voriya.auction.entity.vos.UserVO;
import cn.voriya.auction.mapper.UserMapper;
import cn.voriya.auction.service.IUserService;
import cn.voriya.auction.utils.UserTokenGenerator;
import cn.voriya.framework.email.EmailService;
import cn.voriya.framework.entity.enums.ResultCode;
import cn.voriya.framework.exception.ServiceException;
import cn.voriya.framework.security.context.UserContext;
import cn.voriya.framework.security.enums.VerificationEnums;
import cn.voriya.framework.security.token.Token;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private final UserTokenGenerator userTokenGenerator;
    private final EmailService emailService;
    private final PasswordService passwordService;

    public UserServiceImpl(UserTokenGenerator userTokenGenerator, EmailService emailService, PasswordService passwordService) {
        this.userTokenGenerator = userTokenGenerator;
        this.emailService = emailService;
        this.passwordService = passwordService;
    }


    @Override
    public Token passwordLogin(String account, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //根据用户名或者邮箱查出用户信息
        wrapper.eq("username",account).or().eq("email",account);
        User user = this.getOne(wrapper);
        if (user == null){
            //用户不存在
            throw new ServiceException(ResultCode.USER_NOT_EXIST);
        }
        if (!passwordService.checkPassword(password,user.getPassword())){
            //密码错误
            throw new ServiceException(ResultCode.USER_PASSWORD_ERROR);
        }
        //生成token
        return userTokenGenerator.createToken(user,false);
    }

    @Override
    public Token codeLogin(String email, String code) {
        //校验验证码
        if(!emailService.verifyCode(email, VerificationEnums.LOGIN,code)){
            throw new ServiceException(ResultCode.VERIFICATION_EMAIL_CHECKED_ERROR);
        }
        //根据邮箱查出用户信息
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        User user = this.getOne(wrapper);
        if (user == null){
            //用户不存在,注册
            return register(email);
        }
        //生成token
        return userTokenGenerator.createToken(user,false);
    }

    @Override
    public void updatePassword(String email, String code, String password) {
        //校验验证码
        if(!emailService.verifyCode(email, VerificationEnums.UPDATE_PASSWORD,code)){
            throw new ServiceException(ResultCode.VERIFICATION_EMAIL_CHECKED_ERROR);
        }
        //根据邮箱查出用户信息
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        User user = this.getOne(wrapper);
        if (user == null){
            //用户不存在
            throw new ServiceException(ResultCode.USER_NOT_EXIST);
        }
        //更新密码,并加密
        user.setPassword(passwordService.encodePassword(password));
        this.updateById(user);
    }

    @Override
    public void updateEmail(String email, String code) {
        //获取老邮箱
        final Long id = UserContext.getCurrentUser().getId();
        final User user = this.getById(id);
        //校验验证码
        if(!emailService.verifyCode(user.getEmail(), VerificationEnums.UPDATE_EMAIL,code)){
            throw new ServiceException(ResultCode.VERIFICATION_EMAIL_CHECKED_ERROR);
        }
        //更新邮箱
        user.setEmail(email);
        this.updateById(user);
    }

    @Override
    public void updateUsername(String username) {
        final Long id = UserContext.getCurrentUser().getId();
        final User user = this.getById(id);
        //更新用户名
        user.setUsername(username);
        this.updateById(user);
    }

    private Token register(String email){
        final User user = new User();
        user.setEmail(email);
        //保存用户信息,返回主键
        this.save(user);
        //设置用户名
        user.setUsername("用户"+user.getId());
        //更新用户信息
        this.updateById(user);
        //生成token
        return userTokenGenerator.createToken(user,false);
    }
}
