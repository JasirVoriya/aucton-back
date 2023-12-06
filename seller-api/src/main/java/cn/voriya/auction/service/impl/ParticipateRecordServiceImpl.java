package cn.voriya.auction.service.impl;

import cn.voriya.auction.entity.dos.Goods;
import cn.voriya.auction.entity.dos.ParticipateRecord;
import cn.voriya.auction.entity.dos.User;
import cn.voriya.auction.entity.vos.BidGoodsVO;
import cn.voriya.auction.entity.vos.GoodsVO;
import cn.voriya.auction.mapper.ParticipateRecordMapper;
import cn.voriya.auction.service.IGoodsService;
import cn.voriya.auction.service.IParticipateRecordService;
import cn.voriya.auction.service.IUserService;
import cn.voriya.auction.task.EndBid;
import cn.voriya.auction.task.StartBid;
import cn.voriya.framework.schedule.ScheduledService;
import cn.voriya.framework.security.context.UserContext;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-05
 */
@Service
public class ParticipateRecordServiceImpl extends ServiceImpl<ParticipateRecordMapper, ParticipateRecord> implements IParticipateRecordService {
    private final IGoodsService goodsService;
    private final IUserService userService;
    private final ScheduledService scheduledService;

    public ParticipateRecordServiceImpl(IGoodsService goodsService, IUserService userService, ScheduledService scheduledService) {
        this.goodsService = goodsService;
        this.userService = userService;
        this.scheduledService = scheduledService;
    }

    @Override
    public ParticipateRecord getInfo(Long goodsId, Long applicantId) {
        return this.getOne(new QueryWrapper<ParticipateRecord>()
                .eq("goods_id", goodsId).eq("applicant_id", applicantId));
    }

    @Override
    public Page<BidGoodsVO> getBidGoodsVOPage(Page<ParticipateRecord> participateRecordPage) {
        final Long id = UserContext.getCurrentUser().getId();
        final User user = userService.getById(id);
//        final List<BidGoodsVO> recordVOList = new LinkedList<>();
        final Page<BidGoodsVO> bidGoodsVOPage = new Page<>();
        bidGoodsVOPage.setSize(participateRecordPage.getSize());
        bidGoodsVOPage.setCurrent(participateRecordPage.getCurrent());
        bidGoodsVOPage.setPages(participateRecordPage.getPages());
        bidGoodsVOPage.setTotal(participateRecordPage.getTotal());
        bidGoodsVOPage.setRecords(new LinkedList<>());
        participateRecordPage.getRecords().forEach(item -> {
            final GoodsVO goodsVO = goodsService.getGoodsVO(goodsService.getById(item.getGoodsId()));
            final BidGoodsVO bidGoodsVO = BidGoodsVO.valueOf(goodsVO, item, user);
            bidGoodsVOPage.getRecords().add(bidGoodsVO);
        });
        return bidGoodsVOPage;

    }

    @Override
    public void setGoodsStart(Long goodsId) {
        // 将待开拍的商品状态改为竞价中
        goodsService.update(new UpdateWrapper<Goods>().eq("id", goodsId).set("status", 2));
        // 将参与记录的状态改为竞价中
        this.update(new UpdateWrapper<ParticipateRecord>().eq("goods_id", goodsId).set("status", 2));
    }

    @Override
    public void setGoodsEnd(Long goodsId) {
        // 将待开拍的商品状态改为已结束
        goodsService.update(new UpdateWrapper<Goods>().eq("id", goodsId).set("status", 3));
        // 将参与记录的状态改为已结束
        this.update(new UpdateWrapper<ParticipateRecord>().eq("goods_id", goodsId).set("status", 3));
        // 将出价最高的用户的状态改为已拍下
        this.update(new UpdateWrapper<ParticipateRecord>()
                .eq("goods_id", goodsId)
                .orderByDesc("latest_price")
                .last("limit 1")
                .set("success", true)
        );
    }
}
