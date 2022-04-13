package com.ecommerce.service;

import com.ecommerce.dto.domain.CategoryDTO;
import com.ecommerce.dto.domain.PageCategoryDTO;
import com.ecommerce.dto.domain.PageProductDTO;
import com.ecommerce.dto.domain.ProductDTO;
import com.ecommerce.exception.UniqueConstrainException;

import java.util.Collection;

public interface CategoryService {
    PageCategoryDTO findAll(int page, int size);

    CategoryDTO findById(long id);

    CategoryDTO save(CategoryDTO category);

    CategoryDTO update(CategoryDTO category);

    String addProduct(Long cateId, Long productId);

    String removeProduct(Long cateId, Long productId);

    PageProductDTO getListProducts(Long cateId,int page,int size);
}
