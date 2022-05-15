package com.ecommerce.repositories;

import com.ecommerce.domain.ImportItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ImportItemRepo extends JpaRepository<ImportItem,Long> {
    Page<ImportItem> findByAnImportId(Long id, Pageable pageable);
    ImportItem findByIdImportIdAndIdProductIdAndIdSizeAndIdColor(Long importId,Long productId,String size,String color);
}
