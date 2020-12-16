package com.zjh.mapper;

import com.zjh.entity.TransactionDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * TransactionMapper.
 *
 * @author zhongjinhui
 */
@Mapper
public interface TransactionMapper {

    /**
     * select transaction by id.
     *
     * @param id transaction id
     * @return transaction
     */
    TransactionDO selectById(Long id);

    /**
     * save transaction.
     *
     * @param transactionDO transaction
     */
    void save(TransactionDO transactionDO);

    /**
     * unfreezing amount.
     *
     * @param id     transaction id
     * @param status transaction status
     */
    void updateStatusById(Long id, Integer status);

}
