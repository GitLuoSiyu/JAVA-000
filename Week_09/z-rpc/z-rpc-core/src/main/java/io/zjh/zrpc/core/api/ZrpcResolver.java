package io.zjh.zrpc.core.api;

/**
 * @author zhongjinhui
 */
public interface ZrpcResolver {

    /**
     * resolver server class by name.
     *
     * @param serverClassName target server class name
     * @return server class
     */
    Object resolverByName(String serverClassName);

    /**
     * resolver server class by type.
     *
     * @param clazz target server class type
     * @param <T>   T
     * @return server class
     */
    <T> T resolverByType(Class<T> clazz);

}
