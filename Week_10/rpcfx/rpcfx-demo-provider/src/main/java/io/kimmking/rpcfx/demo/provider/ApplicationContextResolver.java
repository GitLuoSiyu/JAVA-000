package io.kimmking.rpcfx.demo.provider;

import io.kimmking.rpcfx.server.RpcfxResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ApplicationContextResolver.
 *
 * @author onlyonezhongjinhui
 */
public class ApplicationContextResolver implements RpcfxResolver, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public <T> T resolve(Class<T> clazz) {
        return this.applicationContext.getBean(clazz);
    }

}
