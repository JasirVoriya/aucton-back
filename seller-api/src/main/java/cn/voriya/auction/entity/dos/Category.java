package cn.voriya.auction.entity.dos;

import cn.voriya.framework.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

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
 * @since 2023-12-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Category extends BaseEntity {

    /**
     * 分类名称
     */
    private String name;

    /**
     * 父ID，为空则是顶级分类
     */
    private String parentId;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 层级
     */
    private Integer level;
    /**
     * 是否启用
     */
    private Boolean enable;
    /**
     * 分类类型，1：民间珍品，2：司法资产
     */
    private Integer type;
}
