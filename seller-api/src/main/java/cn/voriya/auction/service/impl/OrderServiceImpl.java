package cn.voriya.auction.service.impl;

import cn.voriya.auction.entity.dos.Order;
import cn.voriya.auction.mapper.OrderMapper;
import cn.voriya.auction.service.IOrderService;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
