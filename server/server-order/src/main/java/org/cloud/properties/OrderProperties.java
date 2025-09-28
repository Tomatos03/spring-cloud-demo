package org.cloud.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/25
 */
@Component
@ConfigurationProperties("order")
@Getter @Setter
public class OrderProperties {
    private String orderTimeout;
    private String orderAutoConfirm;
}
