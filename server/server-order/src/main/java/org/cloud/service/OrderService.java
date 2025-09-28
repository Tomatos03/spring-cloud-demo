package org.cloud.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.cloud.entity.Order;
import org.cloud.entity.Product;
import org.cloud.feign.ProductFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/25
 */
@Service
@Slf4j(topic = "orderService")
public class OrderService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private ProductFeign productFeign;


    // 当触发异常的时候默认处理异常顺序是
    // blockHandler -> fallback -> defaultFallback
    @SentinelResource(value = "createOrder", blockHandler = "createOrderExceptionHandler")
    public Order createOrder(Long userId, Long productId) {
        Order order = new Order();
        order.setId(1L);
        //TODO 总金额
        order.setTotalAccount(new BigDecimal("0"));
        order.setUserId(userId);
        order.setNickName("张三");
        order.setAddress("火星");
        //TODO 远程查询商品列表
//        order.setProductList(Arrays.asList(getProductFromRemote(productId)));
//        order.setProductList(Arrays.asList(getProductFromRemoteLoadBalance(productId)));
//        order.setProductList(Arrays.asList(getProductFromRemoteLoadBalancerAnnotation(productId)));
        order.setProductList(Arrays.asList(productFeign.getProductIdById(productId)));
        return order;
    }

    // 处理@SentinelResource注解抛出异常的处理方法, 必须带有BlockException类型成员变量
    public Order createOrderExceptionHandler(Long userId, Long productId, BlockException ex) {
        log.info("handle order exception...");

        Order order = new Order();
        order.setId(0L);
        order.setUserId(0L);
        order.setTotalAccount(new BigDecimal("0"));
        order.setNickName("未知用户");
        order.setAddress(ex.getMessage());
        order.setProductList(List.of());
        return order;
    }

    public Product getProductFromRemoteLoadBalancerAnnotation(Long productId) {
        String url = String.format("http://server-product/productId/%s", productId);

        return restTemplate.getForObject(url, Product.class);
    }


    // 手动实现的远程调用
    public Product getProductFromRemote(Long productId) {
        List<ServiceInstance> instances = discoveryClient.getInstances("server-product");
        ServiceInstance serviceInstance = instances.get(0);

        String url = String.format("http://%s:%s/productId/%s",
                                   serviceInstance.getHost(),
                                   serviceInstance.getPort(),
                                   productId
        );

        log.info("remote invoke url:{}", url);
        return restTemplate.getForObject(url, Product.class);
    }

    // 基于LoadbalancerClient类的负载均衡调用
    public Product getProductFromRemoteLoadBalance(Long productId) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("server-product");

        String url = String.format("http://%s:%s/productId/%s",
                                   serviceInstance.getHost(),
                                   serviceInstance.getPort(),
                                   productId
        );

        log.info("remote invoke url:{}", url);
        return restTemplate.getForObject(url, Product.class);
    }
}