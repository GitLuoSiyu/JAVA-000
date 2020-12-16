package io.zjh.zrpc.core.client;

import com.alibaba.fastjson.JSON;
import io.zjh.zrpc.core.api.ZrpcRequest;
import io.zjh.zrpc.core.api.ZrpcResponse;
import io.zjh.zrpc.core.exception.ZrpcCallException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.util.Objects;

/**
 * ok http z-rpc callable。
 *
 * @author zhongjinhui
 */
public class OkHttpZrpcCallable implements ZrpcCallable {

    private static final MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");
    private final String url;
    private final OkHttpClient client;

    public OkHttpZrpcCallable(String url) {
        this.url = url;
        this.client = new OkHttpClient();
    }

    @Override
    public ZrpcResponse call(final ZrpcRequest request) throws ZrpcCallException {
        final Request req = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON_TYPE, JSON.toJSONString(request)))
                .build();
        try {
            String result = Objects.requireNonNull(client.newCall(req).execute().body()).string();
            return JSON.parseObject(result, ZrpcResponse.class);
        } catch (IOException e) {
            throw new ZrpcCallException(e.getMessage(), e.getCause());
        }
    }

}
