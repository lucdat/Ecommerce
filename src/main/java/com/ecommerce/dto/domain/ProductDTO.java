package com.ecommerce.dto.domain;

import com.ecommerce.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private long id;
    private String code;
    private String name;
    private Boolean newProduct;
    private Boolean bestSeller;
    private Boolean hotProduct;
    private String shortDescription;
    private String detailDescription;
    private Double price;
    private Double competitivePrice;
    private Date addAt;
    Collection<ImageDTO> images;
    Collection<String> sizes;
    Collection<String> colors;
    Collection<DiscountDTO> discounts;
    Collection<CommentDTO> comments;
    Collection<String> tags;
}