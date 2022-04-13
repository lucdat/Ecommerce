package com.ecommerce.service;

import com.ecommerce.dto.domain.ImportItemDTO;

import java.util.Collection;

public interface ImportItemService {
    String add(ImportItemDTO itemDTO);
    String delete(ImportItemDTO itemDTO);
    String update(ImportItemDTO itemDTO);
    int amount();
    Double totalPrice();
    String clear();
    Collection<ImportItemDTO> importItem();
}
