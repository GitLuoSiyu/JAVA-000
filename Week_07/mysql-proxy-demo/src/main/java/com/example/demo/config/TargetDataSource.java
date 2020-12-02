package com.example.demo.config;

import java.lang.annotation.*;

/**
 * @author chentz
 * @version v1.0
 * @date 2020/12/1 17:16
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String value();
}
