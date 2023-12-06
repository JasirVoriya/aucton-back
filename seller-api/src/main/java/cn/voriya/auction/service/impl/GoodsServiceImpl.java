package cn.voriya.auction.service.impl;

import cn.voriya.auction.entity.dos.Category;
import cn.voriya.auction.entity.dos.Goods;
import cn.voriya.auction.entity.dos.User;
import cn.voriya.auction.entity.vos.GoodsVO;
import cn.voriya.auction.mapper.GoodsMapper;
import cn.voriya.auction.service.ICategoryService;
import cn.voriya.auction.service.IGoodsService;
import cn.voriya.auction.service.IParticipateRecordService;
import cn.voriya.auction.service.IUserService;
import cn.voriya.auction.task.EndBid;
import cn.voriya.auction.task.StartBid;
import cn.voriya.framework.schedule.ScheduledService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-03
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    private final IUserService userService;
    private final ICategoryService categoryService;

    public GoodsServiceImpl(IUserService userService, ICategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public GoodsVO getGoodsVO(Goods goods){
        final GoodsVO goodsVO = GoodsVO.valueOf(goods);
        //设置卖家名称
        final User seller = userService.getById(goodsVO.getSellerId());
        goodsVO.setSellerName(seller.getUsername());
        //设置分类名称
        final List<Category> categoryList = categoryService.getBaseMapper().selectBatchIds(goods.getCategoryIds());
        final List<String> categoryNameList = categoryList.stream().map(Category::getName).toList();
        goodsVO.setCategoryNames(categoryNameList);
        return goodsVO;
    }

}
