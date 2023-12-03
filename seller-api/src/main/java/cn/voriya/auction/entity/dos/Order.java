package cn.voriya.auction.entity.dos;

import cn.voriya.framework.mybatis.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class Order extends BaseEntity {

    /**
     * 分类ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long categoryId;

    /**
     * 实际金额
     */
    private Double flowPrice;

    /**
     * 商品ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsId;

    /**
     * 商品封面
     */
    private String cover;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 订单编号
     */
    private String sn;
}
