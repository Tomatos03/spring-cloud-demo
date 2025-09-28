package com.cloud.gateway.fractory;

import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.function.Predicate;

/**
 * 自定义Predicate工厂, 工厂名称必须以RoutePredicateFactory为后缀
 *
 * @author : Tomatos
 * @date : 2025/9/28
 */
@Slf4j(topic = "custom-query-factory")
@Component
public class CustomRoutePredicateFactory extends AbstractRoutePredicateFactory<CustomRoutePredicateFactory.Config> {
    public CustomRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(final Config config) {
        return exchange -> {
            log.info("request predicate...");
            ServerHttpRequest request = exchange.getRequest();
            // request.getQueryParams获取请求参数对应的Map
            String value = request.getQueryParams().getFirst(config.param);
            return StringUtils.hasText(value) && config.value.equals(value);
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("param", "value"); // 定义快捷写法参数顺序: param, value
    }

    @Validated
    public static class Config {
        @NotEmpty
        private String param;
        private String value;

        public String getParam() { return param; }
        public void setParam(String param) { this.param = param; }
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
    }
}
