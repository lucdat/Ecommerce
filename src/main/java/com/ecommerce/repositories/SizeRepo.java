package com.ecommerce.repositories;

import com.ecommerce.domain.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface SizeRepo extends JpaRepository<Size,Long> {
   @Query("select s from Product p join p.sizes s where p.id=?1")
   Collection<Size> getSizeByProductId(Long id);
   Size findBySize(String size);

}
