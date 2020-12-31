package io.kimmking.rpcfx.api;

/**
 * filter interface.
 *
 * @author onlyonezhongjinhui
 */
public interface Filter<T> {

    /**
     * filter.
     *
     * @param request request
     * @return result
     */
    boolean filter(RpcfxRequest<T> request);

}
