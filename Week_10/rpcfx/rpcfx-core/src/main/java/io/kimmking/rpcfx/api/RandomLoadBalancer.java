package io.kimmking.rpcfx.api;

import java.util.List;
import java.util.Random;

/**
 * random load balancer.
 *
 * @author onlyonezhongjinhui
 */
public class RandomLoadBalancer implements LoadBalancer {

    @Override
    public String select(List<String> urls) {
        if (urls.size() <= 0) {
            throw new IllegalArgumentException("no url");
        }
        if (urls.size() <= 1) {
            return urls.get(0);
        }
        int index = new Random().nextInt(urls.size());
        return urls.get(index);
    }

}
