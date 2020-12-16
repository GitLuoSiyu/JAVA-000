package io.kimmking.rpcfx.api;

import lombok.Data;

/**
 * RpcfxRequest.
 *
 * @author zhongjinhui
 */
@Data
public class RpcfxRequest<T> {

    private Class<T> serviceClass;

    private String method;

    private Object[] params;

}
