package io.kimmking.rpcfx.demo.consumer;

import io.kimmking.rpcfx.api.*;
import io.kimmking.rpcfx.client.Rpcfx;
import io.kimmking.rpcfx.client.callable.HttpClientCallable;
import io.kimmking.rpcfx.client.callable.OkHttpCallable;
import io.kimmking.rpcfx.client.callable.RpcfxCallable;
import io.kimmking.rpcfx.demo.api.Order;
import io.kimmking.rpcfx.demo.api.OrderService;
import io.kimmking.rpcfx.demo.api.User;
import io.kimmking.rpcfx.demo.api.UserService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * rpcfx client application.
 *
 * @author onlyonezhongjinhui
 */
@SpringBootApplication
public class RpcfxClientApplication {

    public static void main(String[] args) {
        RpcfxCallable okHttpCallable = new OkHttpCallable();
        String zkUrl = "127.0.0.1:2181";
        Router router = new DefaultRouter();
        LoadBalancer pollingLoadBalancer = new PollingLoadBalancer();

        UserService userService = Rpcfx.createProxy(UserService.class, okHttpCallable, zkUrl, router, pollingLoadBalancer);
        User user = userService.findById(1);
        System.out.println("find user id=1 from server: " + user.getName());

        user = userService.findById(1);
        System.out.println("find user id=1 from server: " + user.getName());

        RpcfxCallable httpClientCallable = new HttpClientCallable();
        LoadBalancer randomLoadBalancer = new RandomLoadBalancer();
        OrderService orderService = Rpcfx.createProxy(OrderService.class, httpClientCallable, zkUrl, router, randomLoadBalancer);
        Order order = orderService.findOrderById(1992129);
        System.out.println(String.format("find order name=%s, amount=%f", order.getName(), order.getAmount()));
        order = orderService.findOrderById(1992129);
        System.out.println(String.format("find order name=%s, amount=%f", order.getName(), order.getAmount()));
    }
}
