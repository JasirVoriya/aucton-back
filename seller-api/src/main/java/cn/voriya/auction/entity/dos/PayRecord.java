package cn.voriya.auction.entity.dos;

import cn.voriya.auction.entity.enums.PayRecordType;
import cn.voriya.framework.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
 * @since 2023-12-06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PayRecord extends BaseEntity {

    /**
     * 付款人id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payerId;

    /**
     * 收款人id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payeeId;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * 支付类型
     */
    private PayRecordType type;

}
