package cn.voriya.auction.entity.dos;

import cn.voriya.framework.mybatis.BaseEntity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ParticipateRecord extends BaseEntity {

    /**
     * 商品id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsId;

    /**
     * 参拍者id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long applicantId;

    /**
     * 是否付款（在成功拍下之后）
     */
    private Boolean pay;
    /**
     * 付款记录id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long paySn;
    /**
     * 是否缴纳保证金
     */
    private Boolean deposit;
    /**
     * 保证金记录id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long depositSn;

    /**
     * 该用户是否成功拍下
     */
    private Boolean success;
    /**
     * 该用户目前最高出价
     */
    private BigDecimal latestPrice;
    /**
     * 状态
     * 0: 未设置
     * 1: 未开始
     * 2: 进行中
     * 3: 已结束
     */
    private Integer status;
}
