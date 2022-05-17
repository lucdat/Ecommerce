package com.ecommerce.service;

import com.ecommerce.dto.domain.CommentDTO;
import com.ecommerce.dto.domain.PageProductDTO;
import com.ecommerce.dto.domain.ProductDTO;
import com.ecommerce.dto.domain.ProductFormDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ProductService {
    PageProductDTO findAll(int activeFlag,int page,int size,int filter);

    ProductDTO findById(Long id);

    ProductDTO save(ProductFormDTO productDTO);

    Map<String,String> update(ProductFormDTO productFormDTO);

    Map<String,String> comment(Long id, CommentDTO commentDTO);

    Map<String,String> uploadImages(Long productId, MultipartFile[] images);

    PageProductDTO findByNameContaining(String name,int page,int size);
}
