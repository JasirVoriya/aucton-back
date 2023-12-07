package cn.voriya.auction.controller.auction;

import cn.voriya.auction.entity.dos.Goods;
import cn.voriya.auction.entity.dos.ParticipateRecord;
import cn.voriya.auction.entity.dos.PayRecord;
import cn.voriya.auction.entity.enums.PayRecordType;
import cn.voriya.auction.entity.vos.BidGoodsVO;
import cn.voriya.auction.entity.vos.GoodsVO;
import cn.voriya.auction.entity.vos.UserBidGoodsNumVO;
import cn.voriya.auction.service.IGoodsService;
import cn.voriya.auction.service.IParticipateRecordService;
import cn.voriya.auction.service.IPayRecordService;
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
@RequestMapping("/participate")
public class ParticipateRecordController {
    private final IGoodsService goodsService;
    private final IParticipateRecordService participateRecordService;
    private final IPayRecordService payRecordService;

    public ParticipateRecordController(IGoodsService goodsService, IParticipateRecordService participateRecordService, IPayRecordService payRecordService) {
        this.goodsService = goodsService;
        this.participateRecordService = participateRecordService;
        this.payRecordService = payRecordService;
    }

    /**
     * 报名参拍并支付
     *
     * @param goodsId 商品id
     */
    @PostMapping
    @Login
    public ResultMessage<Object> participate(Long goodsId) {
        final Goods goods = goodsService.getById(goodsId);
        // 拍卖已结束，不能参拍
        if (goods.getStatus().equals(3)) throw new ServiceException(ResultCode.GOODS_IS_NOT_ON_SALE);
        //参拍人id
        final Long applicantId = UserContext.getCurrentUser().getId();
        //参拍人和卖家是同一个人
        if (applicantId.equals(goods.getSellerId()))
            throw new ServiceException(ResultCode.PARTICIPATE_IS_SELLER);
        ParticipateRecord participateRecord = participateRecordService.getInfo(goodsId, applicantId);
        // 已经报名参拍
        if (participateRecord != null) {
            // 没有支付保证金
            if (!participateRecord.getDeposit())
                throw new ServiceException(ResultCode.PARTICIPATE_BID_INFO_ALREADY_EXIST);
            else throw new ServiceException(ResultCode.PARTICIPATE_BID_INFO_ALREADY_PAY);
        }
        participateRecord = new ParticipateRecord();
        participateRecord.setGoodsId(goodsId);
        participateRecord.setApplicantId(applicantId);
        //设置参拍状态：和商品状态一致
        participateRecord.setStatus(goods.getStatus());
        //如果不需要支付保证金，直接参拍成功
        if (goods.getDeposit().compareTo(new BigDecimal(0)) == 0){
            //设置保证金支付状态：已支付
            participateRecord.setDeposit(true);
            participateRecordService.save(participateRecord);
            return ResultUtil.success();
        }
        //设置保证金支付状态：未支付
        participateRecord.setDeposit(false);
        //保存参拍信息，报名成功，但未支付保证金
        participateRecordService.save(participateRecord);
        //报名人数+1
        goods.setRegistration(goods.getRegistration()+1);
        goodsService.updateById(goods);
        // 支付报名费
        try {
            final PayRecord payRecord = payRecordService.pay(applicantId, goods.getSellerId(), goods.getDeposit(), PayRecordType.Deposit);
            // 更新保证金支付状态：已支付
            participateRecord.setDeposit(true);
            // 更新保证金支付记录id
            participateRecord.setDepositSn(payRecord.getId());
            // 保存到数据库
            participateRecordService.updateById(participateRecord);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.PARTICIPATE_SUCCESS_BUT_PAY_FAIL);
        }
        return ResultUtil.success();
    }
    /**
     * 获取商品参拍信息
     */
    @GetMapping("/{goodsId}")
    @Login
    public ResultMessage<ParticipateRecord> getParticipateBidInfo(@PathVariable Long goodsId) {
        final Long applicantId = UserContext.getCurrentUser().getId();
        ParticipateRecord participateRecord =  participateRecordService.getInfo(goodsId, applicantId);
        return ResultUtil.data(participateRecord);
    }

    /**
     * 支付保证金
     */
    @PostMapping("/pay")
    @Login
    public ResultMessage<Object> payDeposit(Long goodsId) {
        final Goods goods = goodsService.getById(goodsId);
        // 拍卖已结束，不能参拍
        if (goods.getStatus().equals(3)) throw new ServiceException(ResultCode.GOODS_IS_NOT_ON_SALE);
        //参拍人id
        final Long applicantId = UserContext.getCurrentUser().getId();
        ParticipateRecord participateRecord =  participateRecordService.getInfo(goodsId, applicantId);
        // 未报名参拍
        if (participateRecord == null) throw new ServiceException(ResultCode.PARTICIPATE_BID_INFO_NOT_EXIST);
        // 已经支付保证金
        if (participateRecord.getDeposit()) throw new ServiceException(ResultCode.PARTICIPATE_BID_INFO_ALREADY_PAY);
        // 开始支付保证金
        final PayRecord payRecord = payRecordService.pay(applicantId, goods.getSellerId(), goods.getDeposit(), PayRecordType.Deposit);
        // 支付成功，更新参拍信息
        participateRecord.setDeposit(true);
        participateRecord.setDepositSn(payRecord.getId());
        // 保存到数据库
        participateRecordService.updateById(participateRecord);
        return ResultUtil.success();
    }
    /**
     * 获取用户拍卖商品记录（待交保）
     */
    @GetMapping("bid/unpaid")
    @Login
    public ResultMessage<Page<BidGoodsVO>> getUserUnpaidBidGoods(Integer page,Integer size) {
        final Long userId = UserContext.getCurrentUser().getId();
        //获取用户未缴费的参拍商品记录
        Page<ParticipateRecord> unpaidRecord = participateRecordService.getBaseMapper().selectPage(
                new Page<>(page, size),
                new QueryWrapper<ParticipateRecord>()
                        .eq("applicant_id", userId)
                        .eq("deposit", false));
        final Page<BidGoodsVO> bidGoodsVOList = participateRecordService.getBidGoodsVOPage(unpaidRecord);
        return ResultUtil.data(bidGoodsVOList);
    }
    /**
     * 获取用户拍卖商品记录（待开拍）
     */
    @GetMapping("bid/wait")
    @Login
    public ResultMessage<Page<BidGoodsVO>> getUserWaitBidGoods(Integer page,Integer size){
        final Long userId = UserContext.getCurrentUser().getId();
        //获取用户已缴费、待开拍的参拍商品记录
        Page<ParticipateRecord> waitRecord = participateRecordService.getBaseMapper().selectPage(
                new Page<>(page, size),
                new QueryWrapper<ParticipateRecord>()
                        .eq("applicant_id", userId)
                        .eq("deposit", true)
                        .eq("status", 1)
        );
        final Page<BidGoodsVO> bidGoodsVOList = participateRecordService.getBidGoodsVOPage(waitRecord);
        return ResultUtil.data(bidGoodsVOList);
    }
    /**
     * 获取用户拍卖商品记录（竞价中）
     */
    @GetMapping("bid/bidding")
    @Login
    public ResultMessage<Page<BidGoodsVO>> getUserBiddingBidGoods(Integer page,Integer size){
        final Long userId = UserContext.getCurrentUser().getId();
        //获取用户正在参拍商品记录
        Page<ParticipateRecord> biddingRecord = participateRecordService.getBaseMapper().selectPage(
                new Page<>(page, size),
                new QueryWrapper<ParticipateRecord>()
                        .eq("applicant_id", userId)
                        .eq("deposit", true)
                        .eq("status", 2)
        );
        final Page<BidGoodsVO> bidGoodsVOList = participateRecordService.getBidGoodsVOPage(biddingRecord);
        return ResultUtil.data(bidGoodsVOList);
    }
    /**
     * 获取用户拍卖商品记录（已结束）
     */
    @GetMapping("bid/end")
    @Login
    public ResultMessage<Page<BidGoodsVO>> getUserEndBidGoods(Integer page,Integer size){
        final Long userId = UserContext.getCurrentUser().getId();
        //获取已经结束的参拍商品记录
        Page<ParticipateRecord> endRecord = participateRecordService.getBaseMapper().selectPage(
                new Page<>(page, size),
                new QueryWrapper<ParticipateRecord>()
                        .eq("applicant_id", userId)
                        .eq("deposit", true)
                        .eq("success",false)
                        .eq("status", 3)
        );
        final Page<BidGoodsVO> bidGoodsVOList = participateRecordService.getBidGoodsVOPage(endRecord);
        return ResultUtil.data(bidGoodsVOList);
    }
    /**
     * 获取用户拍卖商品记录（已拍下）
     */
    @GetMapping("bid/success")
    @Login
    public ResultMessage<Page<BidGoodsVO>> getUserSuccessBidGoods(Integer page,Integer size){
        final Long userId = UserContext.getCurrentUser().getId();
        //获取成功拍下的商品记录
        Page<ParticipateRecord> successRecord = participateRecordService.getBaseMapper().selectPage(
                new Page<>(page, size),
                new QueryWrapper<ParticipateRecord>()
                        .eq("applicant_id", userId)
                        .eq("deposit", true)
                        .eq("success", true)
        );
        final Page<BidGoodsVO> bidGoodsVOList = participateRecordService.getBidGoodsVOPage(successRecord);
        return ResultUtil.data(bidGoodsVOList);
    }
    /**
     * 获取待交保、待开拍、竞价中、已结束、已拍下的数量
     */
    @GetMapping("bid/num")
    @Login
    public ResultMessage<UserBidGoodsNumVO> getUserAllBidGoodsNum(){
        final Long userId = UserContext.getCurrentUser().getId();
        final UserBidGoodsNumVO userBidGoodsNumVO = new UserBidGoodsNumVO();
        //获取用户未缴费的参拍商品记录数量
        Long unpaidNum = participateRecordService.getBaseMapper().selectCount(
                new QueryWrapper<ParticipateRecord>()
                        .eq("applicant_id", userId)
                        .eq("deposit", false)
        );
        //获取用户已缴费、待开拍的参拍商品记录
        final Long waitNum = participateRecordService.getBaseMapper().selectCount(
                new QueryWrapper<ParticipateRecord>()
                        .eq("applicant_id", userId)
                        .eq("deposit", true)
                        .eq("status", 1)
        );
        //获取用户正在参拍商品记录
        Long biddingNum = participateRecordService.getBaseMapper().selectCount(
                new QueryWrapper<ParticipateRecord>()
                        .eq("applicant_id", userId)
                        .eq("deposit", true)
                        .eq("status", 2)
        );
        //获取已经结束的参拍商品记录
        Long endNum = participateRecordService.getBaseMapper().selectCount(
                new QueryWrapper<ParticipateRecord>()
                        .eq("applicant_id", userId)
                        .eq("deposit", true)
                        .eq("success",false)
                        .eq("status", 3)
        );
        //获取成功拍下的商品记录
        Long successNum = participateRecordService.getBaseMapper().selectCount(
                new QueryWrapper<ParticipateRecord>()
                        .eq("applicant_id", userId)
                        .eq("deposit", true)
                        .eq("success", true)
        );
        userBidGoodsNumVO.setUnpaidNum(unpaidNum);
        userBidGoodsNumVO.setWaitNum(waitNum);
        userBidGoodsNumVO.setBiddingNum(biddingNum);
        userBidGoodsNumVO.setEndNum(endNum);
        userBidGoodsNumVO.setSuccessNum(successNum);
        return ResultUtil.data(userBidGoodsNumVO);
    }
}
