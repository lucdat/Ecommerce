package com.ecommerce.repositories;

import com.ecommerce.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ImageRepo extends JpaRepository<Image,Long> {
    Collection<Image> findByProductId(Long productId);
}
