package io.zjh.zrpc.core.api;

import lombok.Data;

import java.util.List;

/**
 * this is z-rpc request.
 *
 * @author zhongjinhui
 */
@Data
public class ZrpcRequest {

    private String serverClassName;
    private String methodName;
    private List<Object> parameters;
    private String version;

}
