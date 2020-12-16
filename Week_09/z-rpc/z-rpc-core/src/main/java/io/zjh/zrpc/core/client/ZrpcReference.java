package io.zjh.zrpc.core.client;


import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

/**
 * Specify reference service.
 *
 * @author zhongjinhui
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Autowired
public @interface ZrpcReference {

    String url() default "";

    String version() default "";

}
