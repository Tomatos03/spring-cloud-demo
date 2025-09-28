package com.cloud.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 配置全局过滤器, 这个过滤器和之前在路由之中配置的过滤器区别:
 * 路由之中的过滤器需要先通过断言才会经过路由之中的过滤器, 全局过滤
 * 器并不需要先通过断言
 *
 * @author : Tomatos
 * @date : 2025/9/28
 */
@Slf4j(topic = "custom-global-filter")
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String uri = request.getURI()
                            .toString();

        long startTime = System.currentTimeMillis();
        return chain.filter(exchange)
                    .doFinally(doFinally -> {
                        log.info("uri: {}, consume time: {}ms", uri,
                                 System.currentTimeMillis() - startTime);
                    });
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
