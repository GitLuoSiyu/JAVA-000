package com.zjh.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * TransactionDTO.
 *
 * @author zhongjinhui
 */
@Data
public class TransactionDTO implements Serializable {

    /**
     * a user id.
     */
    private Long a;

    /**
     * transaction type,0 - rmb transfer dollar, 1- dollar transfer rmb.
     */
    private Integer aType;

    /**
     * a change rmb.
     */
    private Long aRmb;

    /**
     * a change dollar.
     */
    private Long aDollar;

    /**
     * a transaction id.
     */
    private Long aTransactionId;

    /**
     * b user id.
     */
    private Long b;

    /**
     * transaction type,0 - rmb transfer dollar, 1- dollar transfer rmb.
     */
    private Integer bType;

    /**
     * b change rmb.
     */
    private Long bRmb;

    /**
     * b change dollar.
     */
    private Long bDollar;

    /**
     * b transaction id.
     */
    private Long bTransactionId;
}
