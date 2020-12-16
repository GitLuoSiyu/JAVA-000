package io.kimmking.rpcfx.api;

import io.kimmking.rpcfx.exception.RpcfxException;
import lombok.Data;

/**
 * RpcfxResponse.
 *
 * @author zhongjinhui
 */
@Data
public class RpcfxResponse {

    private Object result;

    private boolean status;

    private RpcfxException exception;
}
