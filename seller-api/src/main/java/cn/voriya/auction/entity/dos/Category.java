package cn.voriya.auction.entity.dos;

import cn.voriya.framework.mybatis.BaseEntity;
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
}
