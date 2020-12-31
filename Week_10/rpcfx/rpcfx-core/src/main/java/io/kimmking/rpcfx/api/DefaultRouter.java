package io.kimmking.rpcfx.api;

import java.util.List;

/**
 * default router.
 *
 * @author onlyonezhongjinhui
 */
public class DefaultRouter implements Router {

    @Override
    public List<String> route(List<String> urls) {
        return urls;
    }

}
