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
public class OrderItemFK implements Serializable {
    @Column(name = "order_id")
    private long orderId;
    @Column(name = "product_id")
    private long productId;
    private String size;
    private String color;
}
