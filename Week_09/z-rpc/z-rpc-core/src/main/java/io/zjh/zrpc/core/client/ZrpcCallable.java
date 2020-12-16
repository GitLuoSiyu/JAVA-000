package io.zjh.zrpc.core.client;

import io.zjh.zrpc.core.api.ZrpcRequest;
import io.zjh.zrpc.core.api.ZrpcResponse;

/**
 * this is a dispatch.
 *
 * @author zhongjinhui
 */
public interface ZrpcCallable {

    ZrpcResponse call(ZrpcRequest request);

}
