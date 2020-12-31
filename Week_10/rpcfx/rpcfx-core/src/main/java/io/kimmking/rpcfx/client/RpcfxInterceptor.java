package io.kimmking.rpcfx.client;

import io.kimmking.rpcfx.api.*;
import io.kimmking.rpcfx.client.callable.RpcfxCallable;
import io.kimmking.rpcfx.exception.RpcfxException;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author onlyonezhongjinhui
 */
public class RpcfxInterceptor<T> implements MethodInterceptor {
    private final List<String> urls = new CopyOnWriteArrayList<>();
    private final Class<T> serviceClass;
    private final RpcfxCallable callable;
    private final Router router;
    private final LoadBalancer loadBalancer;
    private final Filter<T>[] filters;

    public RpcfxInterceptor(final Class<T> serviceClass,
                            final String zkUrl,
                            final RpcfxCallable callable,
                            final Router router,
                            final LoadBalancer loadBalancer,
                            final Filter<T>... filters) {
        this.serviceClass = serviceClass;
        this.callable = callable;
        this.router = router;
        this.loadBalancer = loadBalancer;
        this.filters = filters;
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(zkUrl)
                .retryPolicy(retryPolicy)
                .build();
        client.start();

        try {
            sync(serviceClass, client);
        } catch (Exception e) {
            throw new IllegalStateException("no provider for " + serviceClass.getName());
        }

        client.getData().usingWatcher((CuratorWatcher) watchedEvent -> {
            System.out.println("provider change：" + watchedEvent.getType());
            sync(serviceClass, client);
        });
    }

    @Override
    public Object intercept(final Object o, final Method method, final Object[] objects, final MethodProxy methodProxy) {
        RpcfxRequest<T> request = new RpcfxRequest<>();
        request.setServiceClass(this.serviceClass);
        request.setMethod(method.getName());
        request.setParams(objects);

        List<String> urls = router.route(this.urls);

        String url = loadBalancer.select(urls);

        RpcfxResponse response = callable.call(request, url);
        return handleResponse(response);
    }

    private Object handleResponse(final RpcfxResponse response) {
        if (response.isStatus()) {
            return response.getResult();
        }

        RpcfxException rpcfxException = response.getException();
        if (Objects.nonNull(rpcfxException)) {
            throw rpcfxException;
        }
        throw new RpcfxException("unknown error.");
    }

    private void sync(final Class<T> serviceClass, final CuratorFramework client) throws Exception {
        List<String> providers = client.getChildren().forPath("/rpcfx/" + serviceClass.getName());
        providers.forEach(e -> {
            String[] hostAndPort = new String(e.getBytes()).split("_");
            String url = "http://" + hostAndPort[0] + ":" + hostAndPort[1];
            this.urls.add(url);
        });
    }
}
