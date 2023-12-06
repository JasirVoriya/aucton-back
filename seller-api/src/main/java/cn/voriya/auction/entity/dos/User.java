package cn.voriya.auction.entity.dos;

import cn.voriya.framework.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseEntity {

    private String username;

    @JsonIgnore
    private String password;

    private String email;

    /**
     * 会员生日
     */
    private LocalDateTime birthday;

    /**
     * 会员头像链接
     */
    private String avatar;

    /**
     * 会员性别，0未知，1男，2女
     */
    private Integer sex;

    /**
     * 账户余额
     */
    private BigDecimal money;
}
