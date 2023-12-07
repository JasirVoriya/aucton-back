package cn.voriya.auction.entity.vos;

import cn.voriya.auction.entity.dos.ParticipateRecord;
import cn.voriya.auction.entity.dos.User;
import cn.voriya.auction.entity.enums.GoodsType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class BidGoodsVO {
    /**
     * 卖家名称
     */
    private String sellerName;
    /**
     * 竞拍者名称
     */
    private String bidderName;
    /**
     * 竞拍者最高出价
     */
    private BigDecimal badderLatestPrice;
    /**
     * 商品最高出价
     */
    private BigDecimal goodsLatestPrice;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品封面
     */
    private String goodsCover;
    /**
     * 商品种类
     */
    private GoodsType goodsType;
    /**
     * 商品id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsId;
    /**
     * 最近一次出价时间
     */
    private LocalDateTime latestTime;
    /**
     * 报名记录id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long participatorId;
    /**
     * 竞价状态：
     * 1：待缴纳保证金
     * 2：待开拍
     * 3：竞价中
     * 4：已结束
     * 5：已拍下
     */
    private Integer status;
    /**
     * 是否付款（在成功拍下之后）
     */
    private Boolean pay;
    /**
     * 付款记录id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long paySn;

    public static BidGoodsVO valueOf(GoodsVO goodsVO, ParticipateRecord item, User user) {
        final BidGoodsVO bidGoodsVO = new BidGoodsVO();
        bidGoodsVO.setSellerName(goodsVO.getSellerName());
        bidGoodsVO.setBidderName(user.getUsername());
        bidGoodsVO.setBadderLatestPrice(item.getLatestPrice());
        bidGoodsVO.setGoodsLatestPrice(goodsVO.getLatestPrice());
        bidGoodsVO.setGoodsName(goodsVO.getName());
        bidGoodsVO.setGoodsCover(goodsVO.getCover());
        bidGoodsVO.setGoodsType(GoodsType.valueOf(goodsVO.getGoodsType()));
        bidGoodsVO.setGoodsId(goodsVO.getId());
        bidGoodsVO.setLatestTime(item.getUpdateTime());
        bidGoodsVO.setParticipatorId(item.getApplicantId());
        bidGoodsVO.setPay(item.getPay());
        bidGoodsVO.setPaySn(item.getPaySn());
        if (!item.getDeposit()) {
            //待缴纳保证金
            bidGoodsVO.setStatus(1);
        } else if (item.getSuccess()) {
            //已拍下
            bidGoodsVO.setStatus(5);
        } else if (goodsVO.getStatus().equals(1)) {
            //待开拍
            bidGoodsVO.setStatus(2);
        } else if (goodsVO.getStatus().equals(2)) {
            //竞价中
            bidGoodsVO.setStatus(3);
        } else if (goodsVO.getStatus().equals(3)) {
            //已结束
            bidGoodsVO.setStatus(4);
        }
        return bidGoodsVO;
    }

}
