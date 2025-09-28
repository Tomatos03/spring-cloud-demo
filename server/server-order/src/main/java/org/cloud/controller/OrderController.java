package org.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.cloud.entity.Order;
import org.cloud.properties.OrderProperties;
import org.cloud.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/25
 */
// @RefreshScope注解和 @Value注释实现Nacos配置中心配置更新时变量的同步更新
//@RefreshScope
@RestController
@Slf4j(topic = "orderController")
public class OrderController {
    @Autowired
    private OrderService orderService;

//    @Value("${order-timeout}")
//    private String orderTimeOut;
//    @Value("${order-auto-confirm}")
//    private String orderAutoConfirm;
    @Autowired
    private OrderProperties orderProperties;

    @GetMapping(value = "/create")
    public Order createOrder(@RequestParam(value = "userId", defaultValue = "666") Long userId,
                             @RequestParam(value = "productId") Long productId) {
        return orderService.createOrder(userId, productId);
    }

    @GetMapping(value = "/seckill")
    public Order seckillOrder(@RequestParam("userId") Long userId,
                        @RequestParam("productId") Long productId) {

        Order order = orderService.createOrder(userId, productId);
        order.setId(Long.MAX_VALUE);
        return order;
    }

    // Sentinel流控模式设置/readDB关联/writeDB
    // 当有大量的/writeDB请求的时候, 对/readDB进行限流
    @GetMapping("/readDB")
    public String readDB() {
        log.info("readDB...");
        return "readDB success";
    }

    @GetMapping("/writeDB")
    public String writeDB() {
        return "writeDB success";
    }

    @GetMapping(value = "/config")
    public String getConfig() {
        String orderTimeout = orderProperties.getOrderTimeout();
        String orderAutoConfirm = orderProperties.getOrderAutoConfirm();
        return orderTimeout +  " " + orderAutoConfirm;
    }
}