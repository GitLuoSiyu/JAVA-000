package io.kimmking.rpcfx.client;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.client.callable.RpcfxCallable;
import io.kimmking.rpcfx.exception.RpcfxException;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author zhongjinhui
 */
public class RpcfxInterceptor<T> implements MethodInterceptor {

    private final Class<T> serviceClass;
    private final String url;
    private final RpcfxCallable callable;

    public RpcfxInterceptor(Class<T> serviceClass, String url, RpcfxCallable callable) {
        this.serviceClass = serviceClass;
        this.url = url;
        this.callable = callable;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) {
        RpcfxRequest<T> request = new RpcfxRequest<T>();
        request.setServiceClass(this.serviceClass);
        request.setMethod(method.getName());
        request.setParams(objects);
        RpcfxResponse response = callable.call(request, url);
        return handleResponse(response);
    }

    private Object handleResponse(RpcfxResponse response) {
        if (response.isStatus()) {
            return response.getResult();
        }

        RpcfxException rpcfxException = response.getException();
        if (Objects.nonNull(rpcfxException)) {
            throw rpcfxException;
        }
        throw new RpcfxException("unknown error.");
    }
}
