package com.cloud.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * 自定义断言中的过滤器, 这里实现为指定路径添加token的过滤器.
 * 过滤链名称后缀必须为: GatewayFilterFactory
 *
 * @author : Tomatos
 * @date : 2025/9/28
 */
@Component
public class OnceTokenGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    @Override
    public GatewayFilter apply(NameValueConfig config) {
        /**
         * 调用filter方法进入下一个过滤链, 下一个过滤链执行完成之后, 在回到
         * 调用filter方法进入下一个过滤链, 下一个过滤链执行完成之后, 在回到
         * 当前的过滤链之前, 会先执行then方法之中内容, 直到then方法执行完成才
         * 会重新回到当前的过滤链
         */
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                ServerHttpRequest request = exchange.getRequest();
                HttpHeaders headers = exchange.getResponse().getHeaders();
                String value = config.getValue();
                if (value.equalsIgnoreCase("uuid")) {
                    value = UUID.randomUUID().toString().replaceAll("-", "");
                } else if (value.equalsIgnoreCase("jwt")) {
                    value = "create jwt";
                }
                headers.add(config.getName(), value);
            }));
        };
    }
}
