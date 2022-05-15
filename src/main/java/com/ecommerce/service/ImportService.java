package com.ecommerce.service;

import com.ecommerce.dto.domain.*;
import com.ecommerce.generators.ImportItemFK;

import java.util.HashMap;
import java.util.Map;

public interface ImportService {
    Map<String,String> checkOut(ImportItemService importService);
    PageImport findAll(int page ,int size);

    ImportDTO findById(Long id);

    PageImportItem getImportItems(Long id,int page,int size);

    ImportItemDTO getImportItem(ImportItemID id);
}
