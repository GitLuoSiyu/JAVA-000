package io.kimmking.rpcfx.api;

import lombok.Builder;
import lombok.Data;

/**
 * server provider.
 *
 * @author onlyonezhongjinhui
 */
@Data
@Builder
public class ServiceProvider {
    private String host;
    private String port;
    private String serviceClass;
}
