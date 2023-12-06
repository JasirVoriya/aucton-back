package cn.voriya.auction.service;

import cn.voriya.auction.entity.dos.Goods;
import cn.voriya.auction.entity.vos.GoodsVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-03
 */
public interface IGoodsService extends IService<Goods> {

    /**
     * 将Goods转换成GoodsVO
     * @param goods Goods
     * @return GoodsVO
     */
    GoodsVO getGoodsVO(Goods goods);
}
