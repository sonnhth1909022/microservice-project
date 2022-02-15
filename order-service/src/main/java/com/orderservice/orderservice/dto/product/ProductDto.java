package com.orderservice.orderservice.dto.product;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String name;

    private double price;
    private String description;
    private String detail;
    private String thumbnail;
}
