package io.github.kimmking.gateway.router;

import java.util.List;

public class CustomerHttpEndpointRouter implements HttpEndpointRouter {

    private String proxyServer;

    public CustomerHttpEndpointRouter(String proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public String route(String requestUri) {
        if (requestUri.startsWith("/test")) {
            return proxyServer;
        }
        return "http://localhost:8081"+requestUri;
    }

    @Override
    public String route(List<String> endpoints) {
        return null;
    }
}
