package com.ecommerce.domain;

import com.ecommerce.generators.OrderItemFK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @EmbeddedId
    private OrderItemFK id;
    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Orders order;
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;
    private String name;
    @Column(insertable = false,updatable = false)
    private String size;
    @Column(insertable = false,updatable = false)
    private String color;
    @Column(columnDefinition = "double precision")
    private Double sale;
    @Column(columnDefinition = "double precision")
    private Double totalPrice;
}
