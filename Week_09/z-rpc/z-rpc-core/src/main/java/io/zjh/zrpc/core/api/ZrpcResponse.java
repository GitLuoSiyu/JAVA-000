package io.zjh.zrpc.core.api;

import lombok.Data;

/**
 * @author zhongjinhui
 */
@Data
public class ZrpcResponse {

    private Object result;
    private Boolean status;
    private Exception exception;

}
