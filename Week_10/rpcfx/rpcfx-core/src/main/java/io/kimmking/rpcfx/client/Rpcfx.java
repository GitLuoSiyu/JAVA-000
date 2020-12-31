package io.kimmking.rpcfx.client;


import com.alibaba.fastjson.parser.ParserConfig;
import io.kimmking.rpcfx.api.LoadBalancer;
import io.kimmking.rpcfx.api.Router;
import io.kimmking.rpcfx.client.callable.RpcfxCallable;

public final class Rpcfx {

    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
    }

    public static <T> T createProxy(final Class<T> serviceClass,
                                    final RpcfxCallable callable,
                                    final String zkUrl,
                                    final Router router,
                                    final LoadBalancer loadBalancer) {
        RpcfxProxyFactory factory = RpcfxProxyFactory.getInstance();
        return factory.create(serviceClass, zkUrl, callable, router, loadBalancer);
    }

}
