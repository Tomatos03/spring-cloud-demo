package org.cloud.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/26
 */
@Slf4j(topic = "product-interceptor")
@Component // 这里除了将XTokenRequestInterceptor注册为Bean让其生效外, 还可以在配置文件中配置全类名生效
public class XTokenRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("product-interceptor run...");
        requestTemplate.header("X-Token", UUID.randomUUID().toString());
    }
}