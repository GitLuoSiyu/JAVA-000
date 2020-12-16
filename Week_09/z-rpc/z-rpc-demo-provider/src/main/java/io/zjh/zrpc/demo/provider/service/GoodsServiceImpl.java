package io.zjh.zrpc.demo.provider.service;

import io.zjh.zrpc.core.server.ZrpcServer;
import io.zjh.zrpc.demo.api.IGoodsService;

/**
 * @author zhongjinhui
 */
@ZrpcServer(version = "1.0.0")
public class GoodsServiceImpl implements IGoodsService {

    @Override
    public String findAll() {
        return "goods";
    }

}
