package com.ecommerce.service;

import com.ecommerce.dto.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface BrandService {
    PageBrandDTO findAll(int page, int size);

    BrandDTO findById(long id);

    BrandDTO save(BrandDTO brandDTO);

    BrandDTO update(BrandDTO brandDTO);

    String addProduct(Long brandId, Long productId);

    String removeProduct(Long BrandId, Long productId);

    PageProductDTO getListProducts(Long brandId,int page,int size);

    String uploadLogo(Long brandId, MultipartFile image);
}
