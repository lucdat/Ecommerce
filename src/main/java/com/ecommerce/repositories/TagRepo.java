package com.ecommerce.repositories;

import com.ecommerce.domain.Product;
import com.ecommerce.domain.Size;
import com.ecommerce.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Date;

public interface TagRepo extends JpaRepository<Tag,Long> {
    @Query("select t from Product p join p.tags t where p.id=?1")
    Collection<Tag> getSizeByProductId(Long id);
    Tag findByName(String name);
    @Query("Select p from Tag t join t.products p where t.id =?1 ")
    Page<Product> getProductBySizeId(Long tagId, Pageable pageable);
}
