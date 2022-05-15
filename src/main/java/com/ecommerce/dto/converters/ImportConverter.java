package com.ecommerce.dto.converters;

import com.ecommerce.domain.Import;
import com.ecommerce.domain.ImportItem;
import com.ecommerce.dto.domain.*;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.stream.Collectors;

public class ImportConverter {
    public static ImportDTO covertToDTO(Import anImport){
       ImportDTO dto = new ImportDTO();
       dto.setId(anImport.getId());
       dto.setAmount(anImport.getAmount());
       dto.setTotalPrice(anImport.getTotalPrice());
       dto.setDate(anImport.getDate());
       dto.setDescription(anImport.getDescription());
       dto.setName(anImport.getName());
       dto.setUser(UserConverter.covertToDTO(anImport.getUser()));
       return dto;
    }

    public static PageImport covertToPageImport(Page<Import> list){
        if(list!=null){
            PageImport pageImport = new PageImport();
            Collection<ImportDTO> imports = list.stream().map(ImportConverter::covertToDTO).collect(Collectors.toList());
            PageDTO page = new PageDTO(list.getTotalPages(), imports.size(), list.isFirst(), list.isLast());
            pageImport.setImports(imports);
            pageImport.setPage(page);
            return pageImport;
        }
        return null;
    }
    public static ImportItemDTO covertToImportItemDTO(ImportItem importItem){
       ImportItemDTO dto = new ImportItemDTO();
       dto.setId(importItem.getId());
       dto.setQuantity(importItem.getQuantity());
       dto.setColor(importItem.getColor());
       dto.setSize(importItem.getSize());
       dto.setGender(importItem.getGender());
       dto.setPrice(importItem.getPrice());
       dto.setTotalPrice(importItem.getTotalPrice());
       return dto;
    }

    public static PageImportItem covertToPageImportItem(Page<ImportItem> list){
            PageImportItem pageImportItem = new PageImportItem();
            Collection<ImportItemDTO> imports = list.stream().map(ImportConverter::covertToImportItemDTO).collect(Collectors.toList());
            PageDTO page = new PageDTO(list.getTotalPages(), imports.size(), list.isFirst(), list.isLast());
            pageImportItem.setImportItems(imports);
            pageImportItem.setPage(page);
            return pageImportItem;
    }
}
