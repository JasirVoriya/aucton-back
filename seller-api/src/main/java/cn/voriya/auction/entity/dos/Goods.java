package cn.voriya.auction.entity.dos;

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
 * @since 2023-12-03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Goods extends BaseEntity {

    /**
     * 送拍机构ID
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

    /**
     * 起拍价
     */
    private BigDecimal startingPrice;

    /**
     * 最低加价
     */
    private BigDecimal increment;

    /**
     * 保证金
     */
    private BigDecimal deposit;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    private Boolean deleteFlag;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createBy;

    private String updateBy;
}
