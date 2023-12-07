package cn.voriya.auction.entity.vos;

import cn.hutool.core.bean.BeanUtil;
import cn.voriya.auction.entity.dos.Goods;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class GoodsVO extends Goods {
    /**
     * 分类名称
     */
    private List<String> categoryNames;

    /**
     * 卖家名称
     */
    private String sellerName;

    public static GoodsVO valueOf(Goods goods) {
        GoodsVO goodsVO = new GoodsVO();
        BeanUtil.copyProperties(goods, goodsVO);
        return goodsVO;
    }
}
