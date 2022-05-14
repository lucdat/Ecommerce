package com.ecommerce.service;

import com.ecommerce.dto.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Map;

public interface BrandService {
    PageBrandDTO findAll(int page, int size);

    BrandDTO findById(long id);

    BrandDTO save(BrandDTO brandDTO);

    BrandDTO update(BrandDTO brandDTO);

    Map<String,String> addProduct(Long brandId, Long productId);

    Map<String,String> removeProduct(Long BrandId, Long productId);

    PageProductDTO getListProducts(Long brandId,int page,int size);

    Map<String,String> uploadLogo(Long brandId, MultipartFile image);
}
