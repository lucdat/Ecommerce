package com.ecommerce.repositories;

import com.ecommerce.domain.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ColorRepo extends JpaRepository<Color,Long> {
    @Query("select c from Product p join p.colors c where p.id=?1")
    Collection<Color> getColorByProductId(Long id);
    Color findByColor(String color);
}
