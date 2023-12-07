package cn.voriya.auction.controller.payment;

import cn.voriya.auction.entity.dos.Goods;
import cn.voriya.auction.entity.dos.ParticipateRecord;
import cn.voriya.auction.entity.dos.PayRecord;
import cn.voriya.auction.entity.enums.PayRecordType;
import cn.voriya.auction.service.IGoodsService;
import cn.voriya.auction.service.IParticipateRecordService;
import cn.voriya.auction.service.IPayRecordService;
import cn.voriya.framework.entity.enums.ResultCode;
import cn.voriya.framework.exception.ServiceException;
import cn.voriya.framework.security.annotations.Login;
import cn.voriya.framework.security.context.UserContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-06
 */
@RestController
@RequestMapping("/pay")
public class PayRecordController {
    private final IPayRecordService payRecordService;
    private final IGoodsService goodsService;
    private final IParticipateRecordService participateRecordService;

    public PayRecordController(IPayRecordService payRecordService, IGoodsService goodsService, IParticipateRecordService participateRecordService) {
        this.payRecordService = payRecordService;
        this.goodsService = goodsService;
        this.participateRecordService = participateRecordService;
    }
    /**
     * 成功拍下，付款
     */
    @Login
    @PostMapping("/goods/{goodsId}")
    public void pay(@PathVariable Long goodsId) {
        final Long payerId = UserContext.getCurrentUser().getId();
        // 获取参与记录
        final ParticipateRecord info = participateRecordService.getInfo(goodsId, payerId);
        //记录不存在
        if (info == null) throw new ServiceException(ResultCode.PARTICIPATE_BID_INFO_NOT_EXIST);
        // 拍卖未结束，不能付款
        if (!info.getStatus().equals(3)) throw new ServiceException(ResultCode.GOODS_IS_ON_SALE);
        //没有成功拍下，不能付款
        if (!info.getSuccess()) throw new ServiceException(ResultCode.NOT_BID_SUCCESS);
        //已付款，不能重复付款
        if (info.getPay()) throw new ServiceException(ResultCode.PAY_ALREADY);
        //获取商品信息
        final Goods goods = goodsService.getById(goodsId);
        //付款
        final PayRecord payRecord = payRecordService.pay(payerId, goods.getSellerId(), goods.getLatestPrice(), PayRecordType.PayGoods);
        //更新参与记录：已付款
        info.setPay(true);
        info.setPaySn(payRecord.getId());
        participateRecordService.updateById(info);
    }
}
