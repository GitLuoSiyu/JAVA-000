package io.kimmking.rpcfx.client;


import com.alibaba.fastjson.parser.ParserConfig;
import io.kimmking.rpcfx.client.callable.HttpCallable;
import io.kimmking.rpcfx.client.callable.OkHttpCallable;
import io.kimmking.rpcfx.client.callable.RpcfxCallable;

public final class Rpcfx {

    private static final RpcfxCallable PROTOCOL = new HttpCallable();

    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
    }

    public static <T> T createProxy(final Class<T> serviceClass, final String url) {
        RpcfxProxyFactory factory = RpcfxProxyFactory.getInstance();
        return factory.create(serviceClass, url, PROTOCOL);
    }

}
