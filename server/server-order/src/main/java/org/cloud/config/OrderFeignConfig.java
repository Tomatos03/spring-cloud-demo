package org.cloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/26
 */
@Configuration
public class OrderFeignConfig {

    // 配置OpenFeign调用时的日志级别
    @Bean
    Logger.Level feignLoggerLevel() { // 这类需要导入feign.logger
        return Logger.Level.FULL;
    }

    // 默认情况下retryer不启用, 配置超时重试机制, 这里配置默认实现
//    @Bean
//    Retryer retryer() {
        // public Default(long period, long maxPeriod, int maxAttempts)
        // 默认重试次数maxAttempts = 5次, 第一次尝试需要等待period = 100ms, 第二次尝试需要等待100ms * 1.5
        // 第n次尝试等待 max(1s, 100ms * 1.5^(n - 1)), 即 max(maxPeriod, period * 1.5^(maxAttempts - 1));
//        return new Retryer.Default();
//    }
}
