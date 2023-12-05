package cn.voriya.auction.controller.goods;

import cn.voriya.auction.entity.dos.Category;
import cn.voriya.auction.entity.dos.Goods;
import cn.voriya.auction.entity.vos.GoodsVO;
import cn.voriya.auction.service.ICategoryService;
import cn.voriya.auction.service.IGoodsService;
import cn.voriya.framework.entity.enums.ResultCode;
import cn.voriya.framework.entity.vo.ResultMessage;
import cn.voriya.framework.exception.ServiceException;
import cn.voriya.framework.security.annotations.Login;
import cn.voriya.framework.security.context.UserContext;
import cn.voriya.framework.utils.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-03
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    private final IGoodsService goodsService;
    private final ICategoryService categoryService;

    public GoodsController(IGoodsService goodsService, ICategoryService categoryService) {
        this.goodsService = goodsService;
        this.categoryService = categoryService;
    }

    /**
     * 添加商品
     */
    @PostMapping
    @Login
    public ResultMessage<GoodsVO> addGoods(GoodsVO goodsVO) {
        final Long id = UserContext.getCurrentUser().getId();
        goodsVO.setSellerId(id);
        goodsService.save(goodsVO);
        return ResultUtil.data(goodsVO);
    }
    /**
     * 获取单个商品
     */
    @GetMapping("{id}")
    public ResultMessage<GoodsVO> getGoods( @PathVariable String id) {
        final Goods goods = goodsService.getById(id);
        if(goods == null) throw new ServiceException(ResultCode.GOODS_NOT_EXIST);
        final List<Category> categoryList = categoryService.getBaseMapper().selectBatchIds(goods.getCategoryIds());
        //浏览量+1
        goods.setView(goods.getView() + 1);
        goodsService.updateById(goods);
        //将分类id列表转换为分类名称列表
        final List<String> categoryNameList = categoryList.stream().map(Category::getName).toList();
        final GoodsVO goodsVO = GoodsVO.valueOf(goods);
        goodsVO.setCategoryNames(categoryNameList);
        return ResultUtil.data(goodsVO);
    }
    /**
     * 修改商品
     */
    @PutMapping("{id}")
    public ResultMessage<Object> updateGoods(@PathVariable("id") Long id, String name, Boolean enable) {
        return ResultUtil.data(null);
    }
    /**
     * 删除商品
     */
    @DeleteMapping("{id}")
    public ResultMessage<Object> deleteGoods(@PathVariable("id") Long id) {
        goodsService.removeById(id);
        return ResultUtil.success();
    }
    /**
     * 查询商品列表
     */
    @GetMapping
    public ResultMessage<Page<Goods>> getGoodsList(Integer page, Integer size) {
        final Page<Goods> goodsPage = goodsService.getBaseMapper().selectPage(new Page<>(page, size), new QueryWrapper<>());
        return ResultUtil.data(goodsPage);
    }
}
