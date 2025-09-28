package org.cloud.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.cloud.entity.Product;
import org.cloud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/25
 */
@Slf4j(topic = "productController")
@RequestMapping("/api/product")
@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping(value = "/productId/{id}")
    public Product getProductById(@PathVariable("id") Long productId, HttpServletRequest request) throws InterruptedException {
        log.info("receive invoke");
        log.info("X-Token: {}", request.getHeader("X-Token"));
//        TimeUnit.SECONDS.sleep(2);
        return productService.getProductById(productId);
    }
}
