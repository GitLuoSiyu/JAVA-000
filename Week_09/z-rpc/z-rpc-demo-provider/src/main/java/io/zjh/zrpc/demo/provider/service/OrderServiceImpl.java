package io.zjh.zrpc.demo.provider.service;

import io.zjh.zrpc.core.server.ZrpcServer;
import io.zjh.zrpc.demo.api.IOrderService;

/**
 * @author zhongjinhui
 */
@ZrpcServer(version = "1.0.0")
public class OrderServiceImpl implements IOrderService {

    @Override
    public String findAll() {
        return "orders";
    }

}
