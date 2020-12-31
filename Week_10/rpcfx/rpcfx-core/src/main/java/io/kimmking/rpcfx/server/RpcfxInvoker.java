package io.kimmking.rpcfx.server;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.exception.RpcfxException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * RpcfxInvoker.
 *
 * @author onlyonezhongjinhui
 */
public class RpcfxInvoker {

    private final RpcfxResolver resolver;

    public RpcfxInvoker(RpcfxResolver resolver) {
        this.resolver = resolver;
    }

    public <T> RpcfxResponse invoke(RpcfxRequest<T> request) {
        RpcfxResponse response = new RpcfxResponse();
        Class<T> serviceClass = request.getServiceClass();
        try {
            Method method = resolveMethodFromClass(serviceClass, request.getMethod());
            T target = resolver.resolve(serviceClass);
            Object result = method.invoke(target, request.getParams());
            response.setResult(result);
            response.setStatus(true);
            return response;
        } catch (IllegalAccessException | InvocationTargetException e) {
            response.setException(new RpcfxException(e.getMessage()));
            response.setStatus(false);
            return response;
        }
    }

    private <T> Method resolveMethodFromClass(Class<T> klass, String methodName) {
        return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
    }

}
