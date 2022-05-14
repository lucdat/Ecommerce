package com.ecommerce.dto.domain;

import lombok.Data;

@Data
public class ProductStatistics {
    private int numberOfImport;
    private int quantitySold;
    private int quantityInStock;
    private Double TotalSellPrice;
    private Double TotalImportPrice;
}
