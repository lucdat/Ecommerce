package com.ecommerce.service.impl;

import com.ecommerce.domain.Product;
import com.ecommerce.domain.Tag;
import com.ecommerce.dto.converters.ProductConverter;
import com.ecommerce.dto.converters.TagConverter;
import com.ecommerce.dto.domain.PageProductDTO;
import com.ecommerce.dto.domain.PageTagDTO;
import com.ecommerce.dto.domain.TagDTO;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UniqueConstrainException;
import com.ecommerce.repositories.ProductRepo;
import com.ecommerce.repositories.TagRepo;
import com.ecommerce.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TagServiceImpl implements TagService {
    private final TagRepo tagRepo;
    private final ProductRepo productRepo;
    @Override
    public PageTagDTO findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Tag> tags = tagRepo.findAll(pageable);
        return TagConverter.coverPageTagToPageTagDTO(tags);
    }

    @Override
    public TagDTO save(TagDTO tagDTO) {
        if(tagRepo.findByName(tagDTO.getName())!=null)
            throw new UniqueConstrainException("name:name already exists!");
        Tag tag = TagConverter.coverTagDTOToTag(tagDTO);
        return TagConverter.coverTagToTagDTO(tagRepo.save(tag));
    }

    @Override
    public TagDTO update(TagDTO tagDTO) {
        Tag tag = tagRepo.findById(tagDTO.getId()).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Tag ID S5 not found",tagDTO.getId())));
        if(!tag.getName().equals(tagDTO.getName())){
            if(tagRepo.findByName(tagDTO.getName())!=null)
                throw new UniqueConstrainException("name:name already exists!");
        }
        tag.setName(tag.getName());
        return TagConverter.coverTagToTagDTO(tagRepo.save(tag));
    }

    @Override
    public String addProduct(Long tagId, Long productId) {
        if(tagId!=null&&productId!=null) {
            Tag tag = tagRepo.findById(tagId).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("Tag ID S5 not found",tagId)));
            Product product = productRepo.findById(productId).orElseThrow(
                    () -> new ResourceNotFoundException(String.format("Product ID %s not found",productId)));
            tag.getProducts().add(product);
            product.getTags().add(tag);
            return "success";
        }
        return "error";
    }

    @Override
    public String removeProduct(Long tagId, Long productId) {
        if(tagId!=null&&productId!=null) {
            Tag tag = tagRepo.findById(tagId).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("Tag ID S5 not found",tagId)));
            Product product = productRepo.findById(productId).orElseThrow(
                    () -> new ResourceNotFoundException(String.format("Product ID %s not found",productId)));
            tag.getProducts().remove(product);
            product.getTags().remove(tag);
            return "success";
        }
        return "error";
    }

    @Override
    public PageProductDTO getListProducts(Long tagId, int page, int size) {
        if(tagId!=null&&!tagRepo.existsById(tagId))
                throw new ResourceNotFoundException(String.format("Tag ID S5 not found",tagId));
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Product> products = tagRepo.getProductBySizeId(tagId,pageable);
        return ProductConverter.covertToPageDTO(products);
    }
}
