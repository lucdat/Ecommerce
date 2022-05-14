package com.ecommerce.service.impl;

import com.ecommerce.domain.Discount;
import com.ecommerce.domain.Product;
import com.ecommerce.dto.converters.DiscountConverter;
import com.ecommerce.dto.converters.ProductConverter;
import com.ecommerce.dto.domain.DiscountDTO;
import com.ecommerce.dto.domain.PageDiscountDTO;
import com.ecommerce.dto.domain.PageProductDTO;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repositories.DiscountRepo;
import com.ecommerce.repositories.ProductRepo;
import com.ecommerce.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepo discountRepo;
    private final ProductRepo productRepo;
    @Override
    public PageDiscountDTO findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Discount> discounts = discountRepo.findByActiveFlagIs(true,pageable);
        return DiscountConverter.covertToPageDiscount(discounts);
    }

    @Override
    public DiscountDTO findById(long id) {
        Discount discount = discountRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Discount ID %s not found",id)));
        return DiscountConverter.covertToDTO(discount);
    }

    @Override
    public DiscountDTO save(DiscountDTO discountDTO) {
        Discount discount = DiscountConverter.covertToDiscount(discountDTO);
        return DiscountConverter.covertToDTO(discountRepo.save(discount));
    }

    @Override
    public DiscountDTO update(DiscountDTO dto) {
        Discount discount = discountRepo.findById(dto.getId()).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Discount ID %s not found",dto.getId())));
        discount.setId(dto.getId());
        discount.setStartDate(dto.getStartDate());
        discount.setEndDate(dto.getEndDate());
        discount.setSale(dto.getSale());
        discount.setActiveFlag(dto.getActiveFlag());
        discount.setDetail(dto.getDetail());
        return DiscountConverter.covertToDTO(discountRepo.save(discount));
    }

    @Override
    public Map<String,String> addProduct(Long discountId, Long productId) {
        Map<String,String> response = new HashMap<>();
        if(discountId!=null && productId!=null){
            Discount discount = discountRepo.findById(discountId).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("Discount ID %s not found",discountId)));
            Product product = productRepo.findById(productId).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("Product ID %s not found",productId)));
            discount.getProducts().add(product);
            product.getDiscounts().add(discount);
            response.put("message","success");
        }else response.put("message","error");
        return response;
    }

    @Override
    public Map<String,String> removeProduct(Long discountId, Long productId) {
        Map<String,String> response = new HashMap<>();
        if(discountId!=null && productId!=null){
            Discount discount = discountRepo.findById(discountId).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("Discount ID %s not found",discountId)));
            Product product = productRepo.findById(productId).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("Product ID %s not found",productId)));
            discount.getProducts().remove(product);
            product.getDiscounts().remove(discount);
            response.put("message","success");
        }else response.put("message","error");
        return response;
    }

    @Override
    public PageProductDTO getListProducts(Long discountId, int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Product> products = discountRepo.getListProduct(discountId,pageable);
        return ProductConverter.covertToPageDTO(products);
    }
}
