package io.kimmking.rpcfx.client.callable;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.exception.RpcfxException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;

/**
 * @author zhongjinhui
 */
public class OkHttpCallable implements RpcfxCallable {
    private static final MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client;

    public OkHttpCallable() {
        this.client = new OkHttpClient();
    }

    @Override
    public <T> RpcfxResponse call(RpcfxRequest<T> req, String url) {
        String reqJson = JSON.toJSONString(req);
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON_TYPE, reqJson))
                .build();
        try {
            String result = client.newCall(request).execute().body().string();
            return JSON.parseObject(result, RpcfxResponse.class);
        } catch (IOException e) {
            throw new RpcfxException(e.getMessage());
        }
    }

}
