package cn.voriya.auction.service.impl;

import cn.voriya.auction.entity.dos.Category;
import cn.voriya.auction.mapper.CategoryMapper;
import cn.voriya.auction.service.ICategoryService;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
