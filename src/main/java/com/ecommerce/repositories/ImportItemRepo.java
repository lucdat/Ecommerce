package com.ecommerce.repositories;

import com.ecommerce.domain.ImportItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportItemRepo extends JpaRepository<ImportItem,Long> {
}
