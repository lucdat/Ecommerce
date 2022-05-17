package com.ecommerce.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Statistics {
    private int number;
    private int quantity;
    private Double TotalPrice;
}
