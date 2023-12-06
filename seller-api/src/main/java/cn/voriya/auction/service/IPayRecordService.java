package cn.voriya.auction.service;

import cn.voriya.auction.entity.dos.PayRecord;
import cn.voriya.auction.entity.enums.PayRecordType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-06
 */
public interface IPayRecordService extends IService<PayRecord> {
    /**
     * 支付
     * @param payerId 付款人id
     * @param payeeId 收款人id
     * @param money 交易金额
     * @param payRecordType 支付类型
     */
    void pay(Long payerId, Long payeeId, BigDecimal money, PayRecordType payRecordType);
}
