package cn.voriya.auction.controller.auction;

import cn.voriya.auction.entity.dos.BidRecord;
import cn.voriya.auction.entity.dos.Goods;
import cn.voriya.auction.entity.dos.ParticipateRecord;
import cn.voriya.auction.service.*;
import cn.voriya.framework.entity.enums.ResultCode;
import cn.voriya.framework.entity.vo.ResultMessage;
import cn.voriya.framework.exception.ServiceException;
import cn.voriya.framework.security.annotations.Login;
import cn.voriya.framework.security.context.UserContext;
import cn.voriya.framework.utils.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-05
 */
@RestController
@RequestMapping("/bid-record")
public class BidRecordController {
    private final IBidRecordService bidRecordService;
    private final IParticipateRecordService participateRecordService;
    private final IGoodsService goodsService;
    private final IUserService userService;


    public BidRecordController(IBidRecordService bidRecordService, IParticipateRecordService participateRecordService, IGoodsService goodsService, IUserService userService) {
        this.bidRecordService = bidRecordService;
        this.participateRecordService = participateRecordService;
        this.goodsService = goodsService;
        this.userService = userService;
    }

    /**
     * 出价
     */
    @PostMapping
    @Login
    public ResultMessage<BidRecord> bid(Long goodsId, BigDecimal price) {
//        已报名&&已缴费&&出价>=当前价+最低出价
//        参拍人id
        final Long applicantId = UserContext.getCurrentUser().getId();
//        参拍信息
        final ParticipateRecord info = participateRecordService.getInfo(goodsId, applicantId);
//        参拍信息不存在
        if (info == null) throw new ServiceException(ResultCode.PARTICIPATE_BID_INFO_NOT_EXIST);
//        参拍信息存在但未缴费
        if (!info.getPay()) throw new ServiceException(ResultCode.PARTICIPATE_BID_INFO_ALREADY_EXIST);
//        商品信息
        final Goods goods = goodsService.getById(goodsId);
//        出价小于当前价+最低出价
        if (price.compareTo(goods.getLatestPrice().add(goods.getIncrement())) < 0)
            throw new ServiceException(ResultCode.BID_PRICE_TOO_LOW);
        //判断余额是否充足
        if (userService.getById(applicantId).getMoney().compareTo(price) < 0)
            throw new ServiceException(ResultCode.BALANCE_NOT_ENOUGH);
        final BidRecord bidRecord = new BidRecord();
        bidRecord.setGoodsId(goodsId);
        bidRecord.setPrice(price);
        bidRecord.setBidderId(applicantId);
        bidRecordService.save(bidRecord);
        //更新商品当前价
        goods.setLatestPrice(price);
        goodsService.updateById(goods);
        //更新用户当前最高出价
        info.setLatestPrice(price);
        participateRecordService.updateById(info);
        return ResultUtil.data(bidRecord);
    }

    /**
     * 获取出价记录
     */
    @GetMapping("{goodsId}")
    public ResultMessage<Page<BidRecord>> getBidRecord(@PathVariable String goodsId, Integer page, Integer size) {
        final Page<BidRecord> bidRecordPage = bidRecordService.getBaseMapper().selectPage(
                new Page<>(page, size),
                new QueryWrapper<BidRecord>()
                        .eq("goods_id", goodsId)
                        .orderBy(true, false, "price"));
        return ResultUtil.data(bidRecordPage);
    }

}
