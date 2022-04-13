package com.ecommerce.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {
    private int totalPages;
    private int size;
    private boolean first;
    private boolean last;
}
