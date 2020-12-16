package io.zjh.zrpc.core.server;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * declare z-rpc server.
 *
 * @author zhongjinhui
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ZrpcServerComponent {

    String version() default "";

}
