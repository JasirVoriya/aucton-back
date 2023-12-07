package cn.voriya.auction.service.impl;

import cn.voriya.auction.entity.dos.PayRecord;
import cn.voriya.auction.entity.dos.User;
import cn.voriya.auction.entity.enums.PayRecordType;
import cn.voriya.auction.mapper.PayRecordMapper;
import cn.voriya.auction.service.IPayRecordService;
import cn.voriya.auction.service.IUserService;
import cn.voriya.framework.entity.enums.ResultCode;
import cn.voriya.framework.exception.ServiceException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-06
 */
@Service
public class PayRecordServiceImpl extends ServiceImpl<PayRecordMapper, PayRecord> implements IPayRecordService {
    private final IUserService userService;

    public PayRecordServiceImpl(IUserService userService) {
        this.userService = userService;
    }


    @Override
    public PayRecord pay(Long payerId, Long payeeId, BigDecimal money, PayRecordType type) {
        //不能给自己转账
        if(payeeId.equals(payerId)) throw new ServiceException(ResultCode.NOT_PAY_SELF);
        //获取付款人账户
        final User payer = userService.getById(payerId);
        //获取收款人账户
        final User payee = userService.getById(payeeId);
        //判断账户余额
        if(payer.getMoney().compareTo(money) < 0) throw new ServiceException(ResultCode.BALANCE_NOT_ENOUGH);
        //扣除付款人账户余额
        payer.setMoney(payer.getMoney().subtract(money));
        userService.updateById(payer);
        //增加收款人账户余额
        payee.setMoney(payee.getMoney().add(money));
        userService.updateById(payee);
        //记录支付记录
        PayRecord payRecord = new PayRecord();
        payRecord.setAmount(money);
        payRecord.setPayerId(payerId);
        payRecord.setPayeeId(payeeId);
        payRecord.setType(PayRecordType.Deposit);
        save(payRecord);
        return payRecord;
    }
}
