package cn.voriya.auction.controller.goods;

import cn.hutool.core.bean.BeanUtil;
import cn.voriya.auction.entity.dos.Goods;
import cn.voriya.auction.entity.vos.GoodsVO;
import cn.voriya.auction.service.IGoodsService;
import cn.voriya.auction.service.impl.TaskService;
import cn.voriya.framework.entity.enums.ResultCode;
import cn.voriya.framework.entity.vo.ResultMessage;
import cn.voriya.framework.exception.ServiceException;
import cn.voriya.framework.security.annotations.Login;
import cn.voriya.framework.security.context.UserContext;
import cn.voriya.framework.utils.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-03
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    private final IGoodsService goodsService;
    private final TaskService taskService;

    public GoodsController(IGoodsService goodsService, TaskService taskService) {
        this.goodsService = goodsService;
        this.taskService = taskService;
    }

    /**
     * 添加商品
     */
    @PostMapping
    @Login
    public ResultMessage<GoodsVO> addGoods(GoodsVO goodsVO) {
        final Long id = UserContext.getCurrentUser().getId();
        final LocalDateTime now = LocalDateTime.now();
        goodsVO.setSellerId(id);
        //判断结束时间是否大于现在
        if(goodsVO.getEndTime().isBefore(now)) throw new ServiceException(ResultCode.GOODS_TIME_ERROR);
        //判断开始时间是否大于现在
        if (goodsVO.getStartTime().isBefore(now)) throw new ServiceException(ResultCode.GOODS_TIME_ERROR);
        //判断结束时间是否大于开始时间
        if (goodsVO.getEndTime().isBefore(goodsVO.getStartTime())) throw new ServiceException(ResultCode.GOODS_TIME_ERROR);
        //开始时间必须在当前时间之后的1小时之后
//        if (goodsVO.getStartTime().isBefore(now.plusHours(1))) throw new ServiceException(ResultCode.GOODS_START_TIME_ERROR);
        goodsService.save(goodsVO);
        //添加商品后，开启定时任务
        taskService.addTask(goodsVO.getId(), goodsVO.getStartTime(), goodsVO.getEndTime());
        return ResultUtil.data(goodsVO);
    }

    /**
     * 获取单个商品
     */
    @GetMapping("{id}")
    public ResultMessage<GoodsVO> getGoods(@PathVariable String id) {
        final Goods goods = goodsService.getById(id);
        if (goods == null) throw new ServiceException(ResultCode.GOODS_NOT_EXIST);
        //浏览量+1
        goods.setView(goods.getView() + 1);
        goodsService.updateById(goods);
        //将分类id列表转换为分类名称列表
        final GoodsVO goodsVO = goodsService.getGoodsVO(goods);
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
    public ResultMessage<Page<GoodsVO>> getGoodsList(Integer page, Integer size) {
        final Page<Goods> goodsPage = goodsService.getBaseMapper().selectPage(new Page<>(page, size), new QueryWrapper<>());
        //将Page<Goods>转换成Page<GoodsVO>
        final Page<GoodsVO> goodsVOPage = new Page<>();
        BeanUtil.copyProperties(goodsPage, goodsVOPage);
        goodsVOPage.setRecords(goodsPage.getRecords().stream().map(goodsService::getGoodsVO).toList());
        return ResultUtil.data(goodsVOPage);
    }
}
