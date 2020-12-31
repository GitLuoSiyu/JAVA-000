package io.kimmking.rpcfx.api;

import java.util.List;

/**
 * polling load balancer.
 *
 * @author onlyonezhongjinhui
 */
public class PollingLoadBalancer implements LoadBalancer {
    private int index = 0;

    @Override
    public String select(List<String> urls) {
        synchronized (this) {
            String url;
            if (index < urls.size()) {
                url = urls.get(index++);
            } else {
                index = 0;
                url = urls.get(0);
            }
            return url;
        }
    }

}
