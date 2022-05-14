package com.ecommerce.service;

import com.ecommerce.dto.domain.*;

import java.util.Map;

public interface TagService {
    PageTagDTO findAll(int page, int size);

    TagDTO save(TagDTO tagDTO);

    TagDTO update(TagDTO tagDTO);

    Map<String,String> addProduct(Long tagId, Long productId);

    Map<String,String> removeProduct(Long tagId, Long productId);

    PageProductDTO getListProducts(Long tagId, int page, int size);
}
