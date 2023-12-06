package cn.voriya.auction.entity.dos;

import cn.voriya.auction.entity.enums.GoodsType;
import cn.voriya.framework.mybatis.BaseEntity;
import cn.voriya.framework.mybatis.ListLongToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
@TableName(autoResultMap = true)
public class Goods extends BaseEntity {

    /**
     * 送拍机构ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sellerId;

    /**
     * 分类ID
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    @JsonSerialize(using = ListLongToStringSerializer.class)
    private List<Long> categoryIds;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品详情
     */
    private String intro;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    /**
     * 浏览量
     */
    private Integer view;
    /**
     * 报名人数
     */
    private Integer registration;
    /**
     * 当前价格
     */
    private BigDecimal latestPrice;
    /**
     * 商品类型
     */
    private GoodsType goodsType;
    /**
     * 状态
     * 0: 未设置
     * 1: 未开始
     * 2: 进行中
     * 3: 已结束
     */
    private Integer status;
}
