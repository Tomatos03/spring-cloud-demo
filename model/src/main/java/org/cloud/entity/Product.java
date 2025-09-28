package org.cloud.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/25
 */
@Getter @Setter
public class Product {
    private Long id;
    private BigDecimal price;
    private String productName;
    private Long num;
}
