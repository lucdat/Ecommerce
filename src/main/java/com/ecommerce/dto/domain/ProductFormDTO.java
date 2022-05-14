package com.ecommerce.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFormDTO {
    private long id;
    @NotBlank(message = "The code must be not blank")
    @Size(  min = 2
            ,max=45
            ,message = "The code must be between {min} and {max} characters")
    private String code;
    @NotBlank(message = "The name must be not blank")
    @Size(  min = 1
            ,max=50
            ,message = "The name must be between {min} and {max} characters")
    private String name;
    @Size(  min = 1
            ,max=150
            ,message = "The short description must be between {min} and {max} characters")
    private String shortDescription;
    private String detailDescription;
    private Boolean newProduct;
    private Boolean bestSeller;
    private Boolean hotProduct;
    @Min(0)
    private Double price;
    @Min(1) @Max(2)
    private int gender;
    private Boolean activeFlag;
    private Double competitivePrice;
    Collection<String> sizes;
    Collection<String> colors;
}
