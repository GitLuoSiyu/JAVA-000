package io.kimmking.rpcfx.server;

/**
 * resolver class.
 *
 * @author zhongjinhui
 */
public interface RpcfxResolver {

    /**
     * resolve class.
     *
     * @param clazz target class
     * @return target class
     * @author zhongjinhui
     */
    <T> T resolve(Class<T> clazz);

}
