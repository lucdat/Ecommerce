package com.ecommerce.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageTagDTO {
    Collection<TagDTO> tags;
    PageDTO page;
}
