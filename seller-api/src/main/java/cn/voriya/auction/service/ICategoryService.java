package cn.voriya.auction.service;

import cn.voriya.auction.entity.dos.Category;
import cn.voriya.auction.entity.vos.CategoryVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-03
 */
public interface ICategoryService extends IService<Category> {

    List<CategoryVO> getTopCategory();

    List<CategoryVO> getAllCategory();

    List<CategoryVO> getChildrenCategory(Long id);
}
