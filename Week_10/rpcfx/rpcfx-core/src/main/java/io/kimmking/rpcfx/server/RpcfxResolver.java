package io.kimmking.rpcfx.server;

/**
 * resolver class.
 *
 * @author onlyonezhongjinhui
 */
public interface RpcfxResolver {

    /**
     * resolve class.
     *
     * @param clazz target class
     * @return target class
     * @author onlyonezhongjinhui
     */
    <T> T resolve(Class<T> clazz);

}
