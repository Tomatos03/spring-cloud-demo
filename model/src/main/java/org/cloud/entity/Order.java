package org.cloud.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/25
 */
@Setter @Getter
public class Order {
    private Long id;
    private Long userId;
    private BigDecimal totalAccount;
    private String nickName;
    private String address;
    private List<Product> productList;
}
