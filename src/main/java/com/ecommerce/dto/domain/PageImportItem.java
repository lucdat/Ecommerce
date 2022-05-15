package com.ecommerce.dto.domain;

import lombok.Data;

import java.util.Collection;

@Data
public class PageImportItem {
    Collection<ImportItemDTO> importItems;
    PageDTO page;
}
