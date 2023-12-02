package cn.voriya.auction.entity.vos;

import cn.hutool.core.bean.BeanUtil;
import cn.voriya.auction.entity.dos.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends User {
    public static UserVO valueOf(User user) {
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        return userVO;
    }
}
