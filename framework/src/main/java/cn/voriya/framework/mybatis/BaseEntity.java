package cn.voriya.framework.mybatis;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 数据库基础实体类
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
public abstract class BaseEntity {

    @TableId
    private Long id = null;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Boolean deleteFlag = null;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String createBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
}
