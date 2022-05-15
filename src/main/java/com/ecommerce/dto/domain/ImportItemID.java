package com.ecommerce.dto.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportItemID {
    private long importId;
    private long productId;
    private String size;
    private String color;
}
