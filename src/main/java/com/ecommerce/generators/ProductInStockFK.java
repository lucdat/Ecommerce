package com.ecommerce.generators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductInStockFK implements Serializable {
    @Column(name="color_id")
    private long colorId;
    @Column(name="size_id")
    private long sizeId;
    @Column(name = "product_id")
    private long productId;
}
