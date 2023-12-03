package cn.voriya.auction.service.impl;

import cn.voriya.auction.entity.dos.File;
import cn.voriya.auction.mapper.FileMapper;
import cn.voriya.auction.service.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
