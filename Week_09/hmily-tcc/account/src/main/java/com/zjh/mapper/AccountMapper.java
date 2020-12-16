package com.zjh.mapper;

import com.zjh.entity.AccountDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * accountMapper
 *
 * @author zhongjinhui
 */
@Mapper
public interface AccountMapper {

    /**
     * select account by userId.
     *
     * @param userId account userId
     * @return account
     */
    AccountDO selectById(long userId);

    /**
     * frozen amount.
     *
     * @param id     account id
     * @param dollar dollar
     * @param rmb    rmb
     */
    void frozen(long id, long dollar, long rmb);

    /**
     * unfreezing amount.
     *
     * @param id     account id
     * @param dollar dollar
     * @param rmb    rmb
     */
    void unfreezing(long id, long dollar, long rmb);

}
