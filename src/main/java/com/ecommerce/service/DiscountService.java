package com.ecommerce.service;

import com.ecommerce.dto.domain.*;

public interface DiscountService {
    PageDiscountDTO findAll(int page, int size);

    DiscountDTO findById(long id);

    DiscountDTO save(DiscountDTO discountDTO);

    DiscountDTO update(DiscountDTO discountDTO);

    String addProduct(Long brandId, Long productId);

    String removeProduct(Long BrandId, Long productId);

    PageProductDTO getListProducts(Long discountId, int page, int size);
}
