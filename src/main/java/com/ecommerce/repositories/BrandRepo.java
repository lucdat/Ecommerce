package com.ecommerce.repositories;

import com.ecommerce.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepo extends JpaRepository<Brand,Long> {
    Brand findByName(String name);
}
