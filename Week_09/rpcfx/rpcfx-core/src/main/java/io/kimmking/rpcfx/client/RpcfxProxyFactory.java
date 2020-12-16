package io.kimmking.rpcfx.client;

import io.kimmking.rpcfx.client.callable.RpcfxCallable;
import org.springframework.cglib.proxy.Enhancer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhongjinhui
 */
public class RpcfxProxyFactory {

    private static final RpcfxProxyFactory INSTANCE = new RpcfxProxyFactory();

    public RpcfxProxyFactory() {
    }

    public static RpcfxProxyFactory getInstance() {
        return INSTANCE;
    }

    private static final Map<String, Object> CACHE = new ConcurrentHashMap<>();

    public <T> T create(final Class<T> serviceClass, final String url, final RpcfxCallable protocol) {
        if (CACHE.containsKey(serviceClass.getName())) {
            return (T) CACHE.get(serviceClass.getName());
        }
        synchronized (CACHE) {
            if (CACHE.containsKey(serviceClass.getName())) {
                return (T) CACHE.get(serviceClass.getName());
            }
            T result = doCreate(serviceClass, url, protocol);
            CACHE.putIfAbsent(serviceClass.getName(), result);
            return result;
        }
    }

    private <T> T doCreate(final Class<T> serviceClass, final String url, final RpcfxCallable protocol) {
        return (T) Enhancer.create(serviceClass, new RpcfxInterceptor(serviceClass, url, protocol));
    }

}
