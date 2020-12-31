package io.kimmking.rpcfx.api;

import java.util.List;

/**
 * load balance interface.
 *
 * @author onlyonezhongjinhui
 */
public interface LoadBalancer {

    /**
     * select a url.
     *
     * @param urls url
     * @return url
     */
    String select(List<String> urls);

}
