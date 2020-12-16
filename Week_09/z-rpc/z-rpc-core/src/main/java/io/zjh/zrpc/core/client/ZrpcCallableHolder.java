package io.zjh.zrpc.core.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * z-rpc callable holder.
 *
 * @author zhongjinhui
 */
public class ZrpcCallableHolder {

    private static final Map<String, ZrpcCallable> CALLABLE = new ConcurrentHashMap<>();

    public static void create(String url) {
        CALLABLE.putIfAbsent(url, new OkHttpZrpcCallable(url));
    }

    public static ZrpcCallable get(String url) {
        return CALLABLE.get(url);
    }

}
