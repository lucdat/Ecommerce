package com.ecommerce.domain;

import com.ecommerce.generators.ProductInStockFK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInStock {
    @EmbeddedId
    private ProductInStockFK id;
    private int quantity;
}
