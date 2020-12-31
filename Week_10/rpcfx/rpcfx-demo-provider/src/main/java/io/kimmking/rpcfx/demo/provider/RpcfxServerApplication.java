package io.kimmking.rpcfx.demo.provider;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.demo.api.OrderService;
import io.kimmking.rpcfx.demo.api.UserService;
import io.kimmking.rpcfx.server.RpcfxInvoker;
import io.kimmking.rpcfx.server.RpcfxResolver;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * RpcfxServerApplication.
 *
 * @author onlyonezhongjinhui
 */
@SpringBootApplication
@RestController
public class RpcfxServerApplication {

    public static void main(String[] args) throws Exception {
        String zkUrl = "127.0.0.1:2181";
        String host = "127.0.0.1";
        int port = 8082;

        List<String> services = Arrays.asList(
                "io.kimmking.rpcfx.demo.api.OrderService",
                "io.kimmking.rpcfx.demo.api.UserService");

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(zkUrl).retryPolicy(retryPolicy).build();
        client.start();

        registerServiceProvider(host, port, client, services);

        SpringApplication.run(RpcfxServerApplication.class, args);
    }

    private static void registerServiceProvider(final String host,
                                                final int port,
                                                final CuratorFramework client,
                                                final List<String> services) throws Exception {
        for (String service : services) {
            String servicePath = "/rpcfx/" + service;
            Stat stat = client.checkExists().forPath(servicePath);
            if (Objects.isNull(stat)) {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(servicePath, "service".getBytes());
            }
            String providerPath = servicePath + "/" + host + "_" + port;
            stat = client.checkExists().forPath(providerPath);
            if (Objects.nonNull(stat)) {
                throw new IllegalArgumentException("host " + host + " port " + port + " is already use");
            }
            client.create().withMode(CreateMode.EPHEMERAL).forPath(providerPath, "provider".getBytes());
        }
    }

    @Autowired
    RpcfxInvoker invoker;

    @PostMapping("/")
    public RpcfxResponse invoke(@RequestBody final RpcfxRequest request) {
        return invoker.invoke(request);
    }

    @Bean
    public RpcfxInvoker createInvoker(@Autowired final RpcfxResolver resolver) {
        return new RpcfxInvoker(resolver);
    }

    @Bean
    public RpcfxResolver createResolver() {
        return new ApplicationContextResolver();
    }

    @Bean
    public UserService createUserService() {
        return new UserServiceImpl();
    }

    @Bean
    public OrderService createOrderService() {
        return new OrderServiceImpl();
    }

    /**
     * config fast json http message converter.
     *
     * @author onlyonezhongjinhui
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat, SerializerFeature.WriteClassName);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(mediaTypes);
        return new HttpMessageConverters(fastConverter);
    }

}
