package org.cloud.service;

import org.cloud.entity.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/25
 */
@Service
public class ProductService {
    public Product getProductById(Long productId) {
        Product product = new Product();
        product.setId(productId);
        product.setPrice(new BigDecimal("99"));
        product.setProductName("苹果-" + productId);
        product.setNum(11L);
        return product;
    }
}
