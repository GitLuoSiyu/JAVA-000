package io.zjh.zrpc.core.server;

import io.zjh.zrpc.core.api.ZrpcRequest;
import io.zjh.zrpc.core.api.ZrpcResponse;

/**
 * z-rpc invoker.
 *
 * @author zhongjinhui
 */
public interface ZrpcInvoker {

    /**
     * invoke.
     *
     * @param request request
     * @return response
     */
    ZrpcResponse invoker(ZrpcRequest request);

}
