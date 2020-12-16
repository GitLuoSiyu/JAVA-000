package io.zjh.zrpc.core.server;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.util.StringUtils;

/**
 * z-rpc bean name generator.
 *
 * @author zhongjinhui
 */
public class ZrpcServerBeanNameGenerator extends AnnotationBeanNameGenerator {

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String name = getNameBySpecified(definition);
        if (StringUtils.hasLength(name)) {
            return name;
        }
        return super.generateBeanName(definition, registry);
    }

    private String getNameBySpecified(BeanDefinition beanDefinition) {
        return beanDefinition.getBeanClassName();
    }
}
