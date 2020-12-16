package com.zjh.service;

import com.zjh.dto.TransactionDTO;

/**
 * this is account service.
 *
 * @author zhongjinhui
 */
public interface IAccountService {

    /**
     * try to trade.
     *
     * @param transactionDTO account
     */
    void trade(TransactionDTO transactionDTO);

}
