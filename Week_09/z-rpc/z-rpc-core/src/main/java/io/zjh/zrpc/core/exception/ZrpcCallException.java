package io.zjh.zrpc.core.exception;

/**
 * this is a z-rpc exception.
 *
 * @author zhongjinhui
 */
public class ZrpcCallException extends RuntimeException {

    public ZrpcCallException(String message) {
        super(message);
    }

    public ZrpcCallException(String message, Throwable cause) {
        super(message, cause);
    }
}
