package io.zjh.zrpc.core.server;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * scan handler.
 *
 * @author zhongjinhui
 */
public class ZrpcServerScanner extends ClassPathBeanDefinitionScanner {

    public ZrpcServerScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        addIncludeFilter(new AnnotationTypeFilter(ZrpcServer.class));
        return super.doScan(basePackages);
    }

}
