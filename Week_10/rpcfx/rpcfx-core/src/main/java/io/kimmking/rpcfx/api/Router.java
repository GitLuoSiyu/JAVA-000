package io.kimmking.rpcfx.api;

import java.util.List;

/**
 * router interface.
 *
 * @author onlyonezhongjinhui
 */
public interface Router {

    /**
     * route.
     *
     * @param urls url
     * @return url
     */
    List<String> route(List<String> urls);

}
