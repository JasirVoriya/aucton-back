package cn.voriya.auction.service.impl;

import cn.voriya.auction.entity.dos.Goods;
import cn.voriya.auction.service.IGoodsService;
import cn.voriya.auction.service.IParticipateRecordService;
import cn.voriya.auction.task.EndBid;
import cn.voriya.auction.task.StartBid;
import cn.voriya.framework.schedule.ScheduledService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class TaskService {

    private final ScheduledService scheduledService;
    private final IParticipateRecordService participateRecordService;
    private final IGoodsService goodsService;

    public TaskService(ScheduledService scheduledService, IParticipateRecordService participateRecordService, IGoodsService goodsService) {
        this.scheduledService = scheduledService;
        this.participateRecordService = participateRecordService;
        this.goodsService = goodsService;
    }

    public void addTask(Long id, LocalDateTime startTime, LocalDateTime endTime) {
        new StartBid(id, participateRecordService, scheduledService, startTime);
        new EndBid(id, participateRecordService, scheduledService, endTime);
    }

    /**
     * 项目启动：
     * 开始时间<=now<=结束时间，并且未标记开始：标记开始
     * 开始时间<=结束时间<=now，并且未标记结束：标记结束
     * now<=开始时间<=结束时间：开启定时任务
     */
    @PostConstruct
    public void startCheckBidTime() {
        log.info("开始检查竞价时间");
        //获取所有商品
        final List<Goods> goodsList = goodsService.getBaseMapper().selectList(new QueryWrapper<>());
        goodsList.forEach(goods -> {
            final LocalDateTime now = LocalDateTime.now();
            final LocalDateTime startTime = goods.getStartTime();
            final LocalDateTime endTime = goods.getEndTime();
            //开始时间<=now<=结束时间，并且未标记开始：标记开始
            if (startTime.isBefore(now) && now.isBefore(endTime)&&!goods.getStatus().equals(2)) {
                participateRecordService.setGoodsStart(goods.getId());
                log.info("商品 {} 已标记开始", goods.getId());
            }
            //开始时间<=结束时间<=now，并且未标记结束：标记结束
            if (startTime.isBefore(endTime) && endTime.isBefore(now)&&!goods.getStatus().equals(3)) {
                participateRecordService.setGoodsEnd(goods.getId());
                log.info("商品 {} 已标记结束", goods.getId());
            }
            //now<=开始时间<=结束时间：开启定时任务
            if (now.isBefore(startTime) && startTime.isBefore(endTime)) {
                addTask(goods.getId(), startTime, endTime);
            }
        });
    }
}
