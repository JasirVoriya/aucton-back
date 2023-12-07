package cn.voriya.auction.controller.goods;

import cn.voriya.auction.entity.dos.Category;
import cn.voriya.auction.entity.vos.CategoryVO;
import cn.voriya.auction.service.ICategoryService;
import cn.voriya.framework.entity.enums.ResultCode;
import cn.voriya.framework.entity.vo.ResultMessage;
import cn.voriya.framework.exception.ServiceException;
import cn.voriya.framework.utils.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-04
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 添加分类
     */
    @PostMapping
    public ResultMessage<CategoryVO> addCategory(CategoryVO categoryVO) {
        //判断分类名称是否重复
        final Category categoryByName = categoryService.getOne(new QueryWrapper<Category>().eq("name", categoryVO.getName()));
        if (categoryByName != null) throw new ServiceException(ResultCode.CATEGORY_NAME_EXIST);
        if (categoryVO.getParentId() != null) {
            final Long parentId = Long.valueOf(categoryVO.getParentId());
            final Category parentCategory = categoryService.getById(parentId);
            if (parentCategory == null)
                throw new ServiceException(ResultCode.PARENT_CATEGORY_NOT_EXIST);
            categoryVO.setLevel(parentCategory.getLevel() + 1);
            categoryVO.setType(parentCategory.getType());
        } else {
            categoryVO.setLevel(1);
        }
        categoryService.getBaseMapper().insert(categoryVO);
        final Category category = categoryService.getById(categoryVO.getId());
        return ResultUtil.data(CategoryVO.valueOf(category));
    }

    /**
     * 修改分类
     */
    @PutMapping("{id}")
    public ResultMessage<Object> updateCategory(@PathVariable("id") Long id, String name, Boolean enable) {
        final UpdateWrapper<Category> wrapper = new UpdateWrapper<Category>().eq("id", id);
        if(name != null) wrapper.set("name", name);
        if(enable != null) wrapper.set("enable", enable);
        categoryService.getBaseMapper().update(wrapper);
        return ResultUtil.success();
    }
    /**
     * 查询单个分类
     */
    @GetMapping("{id}")
    public ResultMessage<CategoryVO> getCategory(@PathVariable("id") Long id) {
        final Category category = categoryService.getById(id);
        return ResultUtil.data(CategoryVO.valueOf(category));
    }

    /**
     * 查询顶级分类：民间珍品
     */
    @GetMapping
    public ResultMessage<List<CategoryVO>> getRareGoodsTopCategory() {
        return ResultUtil.data(categoryService.getTopCategory(1));
    }
    /**
     * 查询所有分类：民间珍品
     */
    @GetMapping("/all")
    public ResultMessage<List<CategoryVO>> getRareGoodsAllCategory() {
        return ResultUtil.data(categoryService.getAllCategory(1));
    }
    /**
     * 查询顶级分类：司法资产
     */
    @GetMapping("/judicial/top")
    public ResultMessage<List<CategoryVO>> getJudicialTopCategory() {
        return ResultUtil.data(categoryService.getTopCategory(2));
    }
    /**
     * 查询所有分类：司法资产
     */
    @GetMapping("/judicial/all")
    public ResultMessage<List<CategoryVO>> getJudicialAllCategory() {
        return ResultUtil.data(categoryService.getAllCategory(2));
    }

    /**
     * 查询子分类
     */
    @GetMapping("/{id}/children")
    public ResultMessage<List<CategoryVO>> getChildrenCategory(@PathVariable("id") Long id) {
        return ResultUtil.data(categoryService.getChildrenCategory(id));
    }

    /**
     * 删除分类
     */
    @DeleteMapping("{id}")
    public ResultMessage<Object> deleteCategory(@PathVariable String id) {
        //查询是否有子分类
        final List<Category> categoryList = categoryService.getBaseMapper().selectList(new QueryWrapper<Category>().eq("parent_id", id));
        if(!categoryList.isEmpty())throw new ServiceException(ResultCode.CATEGORY_HAS_CHILDREN);
        categoryService.getBaseMapper().deleteById(id);
        return ResultUtil.success();
    }
}
