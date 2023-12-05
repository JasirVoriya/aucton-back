package cn.voriya.framework.file.service.impl;

import cn.voriya.framework.file.entity.dos.File;
import cn.voriya.framework.file.entity.enums.FileType;
import cn.voriya.framework.file.mapper.FileMapper;
import cn.voriya.framework.file.service.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-03
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

    @SneakyThrows
    @Override
    public Long uploadFile(MultipartFile multipartFile) {
        final File file = new File();
        file.setFilename(multipartFile.getOriginalFilename());
        file.setFile(multipartFile.getBytes());
        file.setType(FileType.fromType(multipartFile.getContentType()));
        this.save(file);
        return file.getId();
    }
}
