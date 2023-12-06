package cn.voriya.auction.service;

import cn.voriya.auction.entity.dos.ParticipateRecord;
import cn.voriya.auction.entity.vos.BidGoodsVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-05
 */
public interface IParticipateRecordService extends IService<ParticipateRecord> {

    ParticipateRecord getInfo(Long goodsId, Long applicantId);

    Page<BidGoodsVO> getBidGoodsVOPage(Page<ParticipateRecord> participateRecordList);

    void setGoodsStart(Long goodsId);

    void setGoodsEnd(Long goodsId);
}
