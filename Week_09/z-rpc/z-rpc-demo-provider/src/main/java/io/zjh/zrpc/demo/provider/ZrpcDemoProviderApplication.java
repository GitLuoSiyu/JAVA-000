package io.zjh.zrpc.demo.provider;

import io.zjh.zrpc.core.server.ZrpcServerScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * z-rpc demo provider application.
 *
 * @author zhongjinhui
 */
@ZrpcServerScan(basePackage = "io.zjh.zrpc.demo.provider.service")
@SpringBootApplication
public class ZrpcDemoProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZrpcDemoProviderApplication.class, args);
    }

}
