package com.ecommerce.service;

import com.ecommerce.dto.domain.ImportItemDTO;

import java.util.Collection;
import java.util.Map;

public interface ImportItemService {
    Map<String,String> add(ImportItemDTO itemDTO);
    Map<String,String> delete(ImportItemDTO itemDTO);
    Map<String,String> update(ImportItemDTO itemDTO);
    int amount();
    Double totalPrice();
    Map<String,String> clear();
    Collection<ImportItemDTO> importItem();
}
