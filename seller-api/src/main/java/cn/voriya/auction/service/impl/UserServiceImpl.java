package cn.voriya.auction.service.impl;

import cn.voriya.auction.entity.dos.User;
import cn.voriya.auction.mapper.UserMapper;
import cn.voriya.auction.service.IUserService;
import cn.voriya.auction.utils.UserTokenGenerator;
import cn.voriya.framework.email.EmailService;
import cn.voriya.framework.entity.enums.ResultCode;
import cn.voriya.framework.exception.ServiceException;
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

    public UserServiceImpl(UserTokenGenerator userTokenGenerator, EmailService emailService) {
        this.userTokenGenerator = userTokenGenerator;
        this.emailService = emailService;
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
        if (!user.getPassword().equals(password)){
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
