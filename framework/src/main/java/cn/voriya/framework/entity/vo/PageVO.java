package cn.voriya.framework.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询参数
 */
@Data
public class PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 页号
     */
    private Integer pageNumber = 0;

    /**
     * 页面大小
     */
    private Integer pageSize = 20;
    /**
     * 排序字段
     */
    private String sort;

    /**
     * 排序方式 asc/desc
     */
    private String order;

    /**
     * 根据页号和页面大小获取起始位置
     */
    public Integer getOffset() {
        return Math.max((pageNumber - 1) * pageSize, 0);
    }

}
