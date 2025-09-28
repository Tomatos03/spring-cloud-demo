package org.cloud.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.cloud.entity.Product;
import org.cloud.feign.ProductFeign;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/26
 */
@Slf4j(topic = "productFeignCallBack")
@Component
public class ProductFeignFallBack implements ProductFeign {
    @Override
    public Product getProductIdById(Long id) {
        log.debug("callback..");
        Product product = new Product();
        product.setId(0L);
        product.setPrice(new BigDecimal(0));
        product.setProductName("未知商品");
        product.setNum(0L);

        return product;
    }
}
