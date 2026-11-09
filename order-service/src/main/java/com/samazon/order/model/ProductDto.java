package com.samazon.order.model;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long productId;
    private String name;
    private Integer stock;
    private BigDecimal currentPrice;
}