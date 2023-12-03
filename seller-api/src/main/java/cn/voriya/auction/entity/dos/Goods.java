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
public class Goods extends BaseEntity {

    /**
     * 店家ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sellerId;
    /**
     * 分类ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long categoryId;
    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品详情
     */
    private String intro;

    /**
     * 商品价格
     */
    private Double price;

    /**
     * 封面路径
     */
    private String cover;
}
