package com.ecommerce.service.impl;

import com.ecommerce.domain.Category;
import com.ecommerce.domain.Product;
import com.ecommerce.dto.converters.ProductConverter;
import com.ecommerce.dto.domain.CategoryDTO;
import com.ecommerce.dto.domain.PageCategoryDTO;
import com.ecommerce.dto.converters.CategoryConverter;
import com.ecommerce.dto.domain.PageProductDTO;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UniqueConstrainException;
import com.ecommerce.repositories.CategoryRepo;
import com.ecommerce.repositories.ProductRepo;
import com.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;

    @Override
    public PageCategoryDTO findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Category> categories= categoryRepo.findAll(pageable);
        return CategoryConverter.covertToPageDTO(categories);
    }

    @Override
    public CategoryDTO findById(long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category ID %S not found",id)));
        return CategoryConverter.covertToDTO(category);
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO)  {
        if(categoryRepo.findByName(categoryDTO.getName())!=null)
            throw new UniqueConstrainException("name:name already exists");
        Category category = CategoryConverter.covertToCategory(categoryDTO);
        return CategoryConverter.covertToDTO(categoryRepo.save(category));
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) {
        Category category = categoryRepo.findById(categoryDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category ID %S not found",categoryDTO.getId())));
        if(!category.getName().equals(categoryDTO.getName())){
            if(categoryRepo.findByName(categoryDTO.getName())!=null)
                throw new UniqueConstrainException("name:name already exists");
        }
        category.setName(categoryDTO.getName());
        return CategoryConverter.covertToDTO(categoryRepo.save(category));
    }

    @Override
    public Map<String,String> addProduct(Long cateId, Long productId) {
        Map<String,String> response = new HashMap<>();
        if(cateId!=null && productId!=null){
            Category category = categoryRepo.findById(cateId).orElseThrow(
                    () -> new ResourceNotFoundException(String.format("Category ID %S not found",cateId)));
            Product product = productRepo.findById(productId).orElseThrow(
                    () -> new ResourceNotFoundException(String.format("Product ID %S not found",productId)));
            category.getProducts().add(product);
            product.setCategory(category);
            response.put("message","success");
        }else response.put("message","error");
        return response;
    }

    @Override
    public Map<String,String> removeProduct(Long cateId, Long productId) {
        Map<String,String> response = new HashMap<>();
        if(cateId!=null && productId!=null){
            Category category = categoryRepo.findById(cateId).orElseThrow(
                    () -> new ResourceNotFoundException(String.format("Category ID %S not found",cateId)));
            Product product = productRepo.findById(productId).orElseThrow(
                    () -> new ResourceNotFoundException(String.format("Product ID %S not found",productId)));
            category.getProducts().remove(product);
            product.setCategory(null);
            response.put("message","success");
        }else response.put("message","error");
        return response;
    }

    @Override
    public PageProductDTO getListProducts(Long cateId,int page,int size) {
        if(cateId!=null&& !categoryRepo.existsById(cateId))
            throw new ResourceNotFoundException(String.format("Category ID %S not found",cateId));

        Pageable pageable = PageRequest.of(page-1,size);
            Page<Product> products = productRepo.findByCategoryIdIs(cateId,pageable);
            return ProductConverter.covertToPageDTO(products);
    }
}
