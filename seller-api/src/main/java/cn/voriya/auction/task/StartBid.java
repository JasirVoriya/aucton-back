package cn.voriya.auction.task;

import cn.voriya.auction.entity.dos.Goods;
import cn.voriya.auction.entity.dos.ParticipateRecord;
import cn.voriya.auction.service.IGoodsService;
import cn.voriya.auction.service.IParticipateRecordService;
import cn.voriya.framework.schedule.ScheduledService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * 开始竞价，将待开拍的商品状态改为竞价中
 * 需要修改表：
 * goods：status=2
 * participate_record：status=2
 *
 * 一共涉及表：goods、participate_record
 */
@Slf4j
public class StartBid implements Runnable{
    private final String taskIdPrefix = "start-bid-task:";
    private final Long goodsId;
    private final IParticipateRecordService participateRecordService;
    private final ScheduledService scheduledService;

    public StartBid(Long goodsId, IParticipateRecordService participateRecordService, ScheduledService scheduledService, LocalDateTime taskTime) {
        this.goodsId = goodsId;
        this.participateRecordService = participateRecordService;
        this.scheduledService = scheduledService;
        this.scheduledService.scheduleTask(taskIdPrefix + goodsId, this, taskTime);
    }

    @Override
    public void run() {
        participateRecordService.setGoodsStart(goodsId);
        log.info("定时任务 {} 已执行", taskIdPrefix+goodsId);
        this.scheduledService.cancelTask(taskIdPrefix + goodsId);
    }
}
