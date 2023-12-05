package cn.voriya.framework.file.entity.dos;

import cn.voriya.framework.file.entity.enums.FileType;
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
public class File extends BaseEntity {

    /**
     * 文件二进制
     */
    private byte[] file;

    /**
     * 文件类型
     */
    private FileType type;
    /**
     * 文件名
     */
    private String filename;
}
