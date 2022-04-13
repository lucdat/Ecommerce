package com.ecommerce.service;

import com.ecommerce.dto.domain.*;

public interface TagService {
    PageTagDTO findAll(int page, int size);

    TagDTO save(TagDTO tagDTO);

    TagDTO update(TagDTO tagDTO);

    String addProduct(Long tagId, Long productId);

    String removeProduct(Long tagId, Long productId);

    PageProductDTO getListProducts(Long tagId, int page, int size);
}
