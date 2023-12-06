package cn.voriya.auction.entity.vos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserBidGoodsNumVO {
    /**
     * 待缴保证金商品
     */
    private Long unpaidNum = 0L;

    /**
     * 待开拍商品
     */
    private Long waitNum = 0L;
    /**
     * 竞价中商品
     */
    private Long biddingNum = 0L;
    /**
     * 已结束商品
     */
    private Long endNum = 0L;
    /**
     * 已拍下商品
     */
    private Long successNum = 0L;
}
