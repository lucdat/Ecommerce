package com.ecommerce.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    @NotBlank(message = "The product ID must be not blank")
    private long productId;
    @NotBlank(message = "The size must be not blank")
    private String size;
    @NotBlank(message = "The color must be not blank")
    private String color;
    @Min(1) @Max(2)
    private int gender;
    @Min(1)
    @Max(1000)
    private int quantity;
    public String key(){
        return this.getProductId()+this.getSize()+this.getColor();
    }
}
