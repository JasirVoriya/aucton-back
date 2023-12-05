package cn.voriya.auction.entity.vos;

import cn.hutool.core.bean.BeanUtil;
import cn.voriya.auction.entity.dos.Goods;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsVO extends Goods {

    private List<String> categoryNames;

    public static GoodsVO valueOf(Goods goods) {
        GoodsVO goodsVO = new GoodsVO();
        BeanUtil.copyProperties(goods, goodsVO);
        return goodsVO;
    }
}
