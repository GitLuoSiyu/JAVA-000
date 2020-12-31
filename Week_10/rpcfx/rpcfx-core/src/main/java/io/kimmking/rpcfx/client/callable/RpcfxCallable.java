package io.kimmking.rpcfx.client.callable;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;

/**
 * @author onlyonezhongjinhui
 */
public interface RpcfxCallable {

    /**
     * invoke request.
     *
     * @param req request
     * @param url url
     * @return RpcfxResponse
     * @author onlyonezhongjinhui
     */
    <T> RpcfxResponse call(RpcfxRequest<T> req, String url);

}
