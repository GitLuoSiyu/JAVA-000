package io.zjh.zrpc.core.server;

import java.lang.annotation.*;

/**
 * declare z-rpc server.
 *
 * @author zhongjinhui
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZrpcServer {

    String version() default "";

}
