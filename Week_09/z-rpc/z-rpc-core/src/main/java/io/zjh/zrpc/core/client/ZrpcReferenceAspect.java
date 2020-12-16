package io.zjh.zrpc.core.client;

import io.zjh.zrpc.core.api.ZrpcRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * z-rpc reference aspect.
 *
 * @author zhongjinhui
 */
@Aspect
@Component
public class ZrpcReferenceAspect {

    @Around("@within(io.zjh.zrpc.core.client.ZrpcReference)")
    public Object around(ProceedingJoinPoint joinPoint) {
        ZrpcReference annotation = joinPoint.getTarget().getClass().getAnnotation(ZrpcReference.class);
        ZrpcCallable callable = ZrpcCallableHolder.get(annotation.url());
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        ZrpcRequest request = new ZrpcRequest();
        request.setServerClassName(joinPoint.getClass().getName());
        request.setMethodName(method.getName());
        request.setParameters(Arrays.asList(joinPoint.getArgs()));
        request.setVersion(annotation.version());
        return callable.call(request);
    }

}
