package com.zjh.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * transaction.
 *
 * @author zhongjinhui
 */
@Data
public class TransactionDO implements Serializable {

    /**
     * primary key.
     */
    private Long id;

    /**
     * user id.
     */
    private Long userId;

    /**
     * frozen dollar.
     */
    private Long dollar;

    /**
     * frozen rmb.
     */
    private Long rmb;

    /**
     * transaction status, 0 - to be checked, 1 - complete, 2 - failed.
     */
    private Integer status;

    /**
     * create time.
     */
    private Long createTime;

    /**
     * update time.
     */
    private Long updateTime;

}