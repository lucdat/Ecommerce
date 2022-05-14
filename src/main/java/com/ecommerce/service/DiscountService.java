package com.ecommerce.service;

import com.ecommerce.dto.domain.*;

import java.util.Map;

public interface DiscountService {
    PageDiscountDTO findAll(int page, int size);

    DiscountDTO findById(long id);

    DiscountDTO save(DiscountDTO discountDTO);

    DiscountDTO update(DiscountDTO discountDTO);

    Map<String,String> addProduct(Long brandId, Long productId);

    Map<String,String> removeProduct(Long BrandId, Long productId);

    PageProductDTO getListProducts(Long discountId, int page, int size);
}
