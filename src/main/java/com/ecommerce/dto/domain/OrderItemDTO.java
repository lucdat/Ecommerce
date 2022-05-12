package com.ecommerce.dto.domain;

import com.ecommerce.generators.OrderItemFK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private OrderItemFK id;
    @JsonIgnore
    @NotBlank(message = "The product id must be not blank")
    private Long productId;
    private String name;
    @NotBlank(message = "The size must be not blank")
    private String size;
    @NotBlank(message = "The color must be not blank")
    private String color;
    private Double price;
    private int gender;
    @Min(1)
    private int quantity;
    private Double sale;
    private Double totalPrice;
}
