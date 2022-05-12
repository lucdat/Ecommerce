package com.ecommerce.repositories;

import com.ecommerce.domain.ProductInStock;
import com.ecommerce.generators.ProductInStockFK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductInStockRepo extends JpaRepository<ProductInStock, ProductInStockFK> {
    List<ProductInStock> findByIdColorIdAndIdSizeIdAndIdProductIdAndIdGender(Long colorId, Long sizeId, Long productId,int gender);
}
