package com.zjh.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zjh.dto.TransactionDTO;
import com.zjh.entity.AccountDO;
import com.zjh.entity.TransactionDO;
import com.zjh.mapper.AccountMapper;
import com.zjh.mapper.TransactionMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * accountServiceImpl
 *
 * @author zhongjinhui
 */
@Slf4j
@Service
@Component
public class AccountServiceImpl implements IAccountService {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private TransactionMapper transactionMapper;

    @HmilyTCC(confirmMethod = "confirmTransaction", cancelMethod = "cancelTransaction")
    @Override
    public void trade(TransactionDTO transactionDTO) {
        AccountDO aAccount = accountMapper.selectById(transactionDTO.getA());
        AccountDO bAccount = accountMapper.selectById(transactionDTO.getB());

        boolean typeIsOk = (transactionDTO.getAType() == 0 && transactionDTO.getAType() == 1)
                || (transactionDTO.getAType() == 1 && transactionDTO.getAType() == 0);
        if (!typeIsOk) {
            throw new IllegalArgumentException("trade type error.");
        }

        if (transactionDTO.getAType() == 0) {
            Long rmb = transactionDTO.getARmb() * 100;
            if (aAccount.getRmbBalance().compareTo(rmb) < 0) {
                throw new IllegalArgumentException("Sorry, your rmb balance is not enough");
            }
        } else {
            Long dollar = transactionDTO.getADollar() * 100;
            if (aAccount.getDollarBalance().compareTo(dollar) < 0) {
                throw new IllegalArgumentException("Sorry, your dollar balance is not enough");
            }
        }
        if (transactionDTO.getBType() == 0) {
            Long rmb = transactionDTO.getBRmb() * 100;
            if (bAccount.getRmbBalance().compareTo(rmb) < 0) {
                throw new IllegalArgumentException("Sorry, your rmb balance is not enough");
            }
        } else {
            Long dollar = transactionDTO.getBDollar() * 100;
            if (bAccount.getDollarBalance().compareTo(dollar) < 0) {
                throw new IllegalArgumentException("Sorry, your dollar balance is not enough");
            }
        }

        long millis = System.currentTimeMillis();
        if (transactionDTO.getAType() == 0) {
            accountMapper.frozen(transactionDTO.getA(), -transactionDTO.getARmb() * 100, transactionDTO.getADollar() * 100);
            accountMapper.frozen(transactionDTO.getB(), transactionDTO.getBRmb() * 100, -transactionDTO.getBDollar() * 100);

            TransactionDO aTransaction = new TransactionDO();
            aTransaction.setUserId(transactionDTO.getA());
            aTransaction.setRmb(-transactionDTO.getARmb() * 100);
            aTransaction.setDollar(transactionDTO.getADollar() * 100);
            aTransaction.setStatus(0);
            aTransaction.setCreateTime(millis);

            TransactionDO bTransaction = new TransactionDO();
            bTransaction.setUserId(transactionDTO.getB());
            bTransaction.setRmb(transactionDTO.getBRmb() * 100);
            bTransaction.setDollar(-transactionDTO.getBDollar() * 100);
            bTransaction.setStatus(0);
            bTransaction.setCreateTime(millis);

            transactionMapper.save(aTransaction);
            transactionMapper.save(bTransaction);

            transactionDTO.setATransactionId(aTransaction.getId());
            transactionDTO.setBTransactionId(bTransaction.getId());
        } else {
            accountMapper.frozen(transactionDTO.getA(), transactionDTO.getARmb(), -transactionDTO.getADollar());
            accountMapper.frozen(transactionDTO.getB(), -transactionDTO.getBRmb(), transactionDTO.getBDollar());

            TransactionDO aTransaction = new TransactionDO();
            aTransaction.setUserId(transactionDTO.getA());
            aTransaction.setRmb(transactionDTO.getARmb() * 100);
            aTransaction.setDollar(-transactionDTO.getADollar() * 100);
            aTransaction.setStatus(0);
            aTransaction.setCreateTime(millis);

            TransactionDO bTransaction = new TransactionDO();
            bTransaction.setUserId(transactionDTO.getB());
            bTransaction.setRmb(-transactionDTO.getBRmb() * 100);
            bTransaction.setDollar(transactionDTO.getBDollar() * 100);
            bTransaction.setStatus(0);
            bTransaction.setCreateTime(millis);

            transactionMapper.save(aTransaction);
            transactionMapper.save(bTransaction);

            transactionDTO.setATransactionId(aTransaction.getId());
            transactionDTO.setBTransactionId(bTransaction.getId());
        }
    }

    public void confirmTransaction(TransactionDTO transactionDTO) {
        log.info("confirmTransaction......");
        accountMapper.unfreezing(transactionDTO.getA(), transactionDTO.getARmb() * 100, -transactionDTO.getADollar() * 100);
        accountMapper.unfreezing(transactionDTO.getB(), +transactionDTO.getBRmb() * 100, transactionDTO.getBDollar() * 100);

        transactionMapper.updateStatusById(transactionDTO.getATransactionId(), 1);
        transactionMapper.updateStatusById(transactionDTO.getBTransactionId(), 1);
    }

    public void cancelTransaction(TransactionDTO transactionDTO) {
        log.info("cancelTransaction......");
        transactionMapper.updateStatusById(transactionDTO.getATransactionId(), 2);
        transactionMapper.updateStatusById(transactionDTO.getBTransactionId(), 2);
    }

}
