package com.ecommerce.dto.domain;

import com.ecommerce.generators.ImportItemFK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportItemDTO {
    private ImportItemFK id;
    @NotBlank(message = "Code must be not blank")
    private String code;
    @NotBlank(message = "Name must be not blank")
    private String name;
    @NotBlank(message = "size must be not blank")
    private String size;
    @NotBlank(message = "color must be not blank")
    private String color;
    @Min(1) @Max(2)
    private int gender;
    @Min(1)
    @Max(10000)
    private int quantity;
    @Min(1)
    private Double price;
    private double totalPrice;
    public String getKey(){
        return this.code+this.color+this.size;
    }
}
