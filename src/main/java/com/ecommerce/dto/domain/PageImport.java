package com.ecommerce.dto.domain;

import lombok.Data;

import java.util.Collection;

@Data
public class PageImport {
    Collection<ImportDTO> imports;
    PageDTO page;
}
