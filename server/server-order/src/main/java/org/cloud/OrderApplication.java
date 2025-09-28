package org.cloud;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/25
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient // 导入DiscoveryClient
@EnableFeignClients // 导入FeignClient
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(NacosConfigManager nacosConfigManager) {
        log.info("applicationRunner...");
        return args -> {
            ConfigService configService = nacosConfigManager.getConfigService();
            configService.addListener("server-order.yml", "DEFAULT_GROUP", new Listener() {
                @Override
                public Executor getExecutor() {
                    return Executors.newFixedThreadPool(4);
                }

                @Override
                public void receiveConfigInfo(String s) {
                    log.info("receive config info :{}", s);
                    log.info("other operation...");
                }
            });
        };
    }
}
