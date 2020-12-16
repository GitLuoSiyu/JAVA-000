package com.zjh.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * account.
 *
 * @author zhongjinhui
 */
@Data
public class AccountDO implements Serializable {

    /**
     * primary key.
     */
    private Long id;

    /**
     * user id.
     */
    private Long userId;

    /**
     * dollar balance.
     */
    private Long dollarBalance;

    /**
     * rmb balance.
     */
    private Long rmbBalance;

    /**
     * create time.
     */
    private Long createTime;

    /**
     * update time.
     */
    private Long updateTime;

}