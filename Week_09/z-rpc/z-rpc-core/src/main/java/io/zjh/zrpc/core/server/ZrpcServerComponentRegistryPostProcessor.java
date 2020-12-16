package io.zjh.zrpc.core.server;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

/**
 * @author zhongjinhui
 */
@Component
public class ZrpcServerComponentRegistryPostProcessor implements PriorityOrdered, BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        String[] beanNamesForAnnotation = configurableListableBeanFactory.getBeanNamesForAnnotation(ZrpcServer.class);
        if (beanNamesForAnnotation.length > 0) {
            for (String beanName : beanNamesForAnnotation) {
                Class<?> clazz = configurableListableBeanFactory.getType(beanName);
                assert clazz != null;
                if (clazz.getAnnotations().length > 0) {
                    for (Annotation annotation : clazz.getAnnotations()) {
                        if (annotation instanceof ZrpcServerComponent) {
                            registerAlias(configurableListableBeanFactory, beanName, clazz.getName());
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    private void registerAlias(ConfigurableListableBeanFactory factory, String beanId, String value) {
        if (!factory.containsBeanDefinition(value)) {
            factory.registerAlias(beanId, value);
        }
    }

}
