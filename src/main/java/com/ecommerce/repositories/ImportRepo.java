package com.ecommerce.repositories;

import com.ecommerce.domain.Import;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportRepo extends JpaRepository<Import,Long> {
}
