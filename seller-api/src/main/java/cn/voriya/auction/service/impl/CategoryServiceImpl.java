package cn.voriya.auction.service.impl;

import cn.voriya.auction.entity.dos.Category;
import cn.voriya.auction.entity.vos.CategoryVO;
import cn.voriya.auction.mapper.CategoryMapper;
import cn.voriya.auction.service.ICategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-03
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Override
    public List<CategoryVO> getTopCategory(Integer type) {
        final List<Category> categoryList = this.baseMapper.selectList(new QueryWrapper<Category>()
                .eq("level", 1)
                .eq("type", type)
        );
        //将categoryList转换为CategoryVOList
        return categoryList.stream().map(CategoryVO::valueOf).toList();
    }

    @Override
    public List<CategoryVO> getAllCategory(Integer type) {
        final List<CategoryVO> categoryVOList = this.getTopCategory(type);
        categoryVOList.forEach(categoryVO -> {
            final List<CategoryVO> children = this.getChildrenCategory(categoryVO.getId());
            categoryVO.setChildren(children);
            if(!children.isEmpty()){
                children.forEach(categoryVO1 -> {
                    final List<CategoryVO> children1 = this.getChildrenCategory(categoryVO1.getId());
                    categoryVO1.setChildren(children1);
                });
            }
        });
        return categoryVOList;
    }

    @Override
    public List<CategoryVO> getChildrenCategory(Long id) {
        final List<Category> categoryList = this.baseMapper.selectList(new QueryWrapper<Category>().eq("parent_id", id));
        //将categoryList转换为CategoryVOList
        return categoryList.stream().map(CategoryVO::valueOf).toList();
    }
}
