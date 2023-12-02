package cn.voriya.auction.service.impl;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    public String encodePassword(String rawPassword) {
        // 生成加密后的密码
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public boolean checkPassword(String rawPassword, String hashedPassword) {
        // 验证密码是否匹配
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
