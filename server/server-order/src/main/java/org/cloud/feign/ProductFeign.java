package org.cloud.feign;

import org.cloud.entity.Product;
import org.cloud.feign.fallback.ProductFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 利用FeignClient实现的远程调用, 调用策略默认使用均衡负载
 *
 * @author : Tomatos
 * @date : 2025/9/26
 */
// 回调属性fallback需要配置组件sentinel
@FeignClient(value = "server-product", fallback = ProductFeignFallBack.class, path = "/api/product")
public interface ProductFeign {
    @GetMapping("/productId/{id}")
    Product getProductIdById(@PathVariable("id") Long id);
}
