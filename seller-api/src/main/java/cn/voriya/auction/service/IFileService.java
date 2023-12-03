package cn.voriya.auction.service;

import cn.voriya.auction.entity.dos.File;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-03
 */
public interface IFileService extends IService<File> {

    Long uploadFile(MultipartFile file);
}
