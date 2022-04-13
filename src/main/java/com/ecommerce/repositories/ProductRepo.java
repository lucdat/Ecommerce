package com.ecommerce.repositories;

import com.ecommerce.domain.Product;
import com.ecommerce.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ProductRepo extends JpaRepository<Product,Long> {
    Page<Product> findByActiveFlagIs(Boolean value,Pageable pageable);
    Product findByCode(String code);
    Page<Product> findByCategoryIdIs(Long value, Pageable pageable);
    Page<Product> findByBrandIdIs(Long value, Pageable pageable);
    Page<Product> findByNameContaining(String name,Pageable pageable);
}
