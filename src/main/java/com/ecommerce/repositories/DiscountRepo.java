package com.ecommerce.repositories;

import com.ecommerce.domain.Discount;
import com.ecommerce.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface DiscountRepo extends JpaRepository<Discount,Long> {
    @Query("select d from Product p join p.discounts d where p.id=?1 and d.activeFlag=true")
    Collection<Discount> getSizeByProductId(Long id);
    Page<Discount> findByActiveFlagIs(Boolean active,Pageable pageable);
    @Query("select p from Discount d join d.products p where d.id=?1")
    Page<Product> getListProduct(long discountId,Pageable pageable);
}
