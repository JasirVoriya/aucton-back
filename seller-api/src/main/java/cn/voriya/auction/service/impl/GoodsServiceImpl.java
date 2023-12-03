package cn.voriya.auction.service.impl;

import cn.voriya.auction.entity.dos.Goods;
import cn.voriya.auction.mapper.GoodsMapper;
import cn.voriya.auction.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-03
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

}
