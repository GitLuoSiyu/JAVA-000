package io.zjh.zrpc.core.server;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * reference service scan.
 *
 * @author zhongjinhui
 */
@Import(ZrpcServerRegistry.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface ZrpcServerScan {

    String[] basePackage() default {};

}
