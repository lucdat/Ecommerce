package com.ecommerce.dto.converters;

import com.ecommerce.domain.*;
import com.ecommerce.dto.domain.*;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class ProductConverter {
    public static ProductDTO covertToDTO(Product product){
        if(product!=null){
            ProductDTO dto = new ProductDTO();
            Collection<ImageDTO> images = new ArrayList<>();
            for (Image image : product.getImages()) {
                ImageDTO imageDTO = new ImageDTO(image.getImage());
                images.add(imageDTO);
            }
            Collection<String> sizes = new ArrayList<>();
            for (Size s : product.getSizes()) {
                String size = s.getSize();
                sizes.add(size);
            }
            Collection<String> colors = new ArrayList<>();
            for (Color c : product.getColors()) {
                String color = c.getColor();
                colors.add(color);
            }
            Collection<DiscountDTO> discounts = new ArrayList<>();
            for (Discount d : product.getDiscounts()){
                discounts.add(DiscountConverter.covertToDTO(d));
            }
            Collection<String> tags = new ArrayList<>();
            for(Tag t : product.getTags()){
                String tagName = t.getName();
                tags.add(tagName);
            }
            Collection<CommentDTO> comments = new ArrayList<>();
            for(Comment c: product.getComments()){
                CommentDTO comment = new CommentDTO();
                comment.setUsername(c.getUser().getUsername());
                comment.setMessage(c.getMessage());
                comment.setDate(c.getDate());
                comment.setUserId(c.getUser().getId());
                comments.add(comment);
            }
            dto.setId(product.getId());
            dto.setCode(product.getCode());
            dto.setName(product.getName());
            dto.setBestSeller(product.getBestSeller());
            dto.setHotProduct(product.getHotProduct());
            dto.setNewProduct(product.getNewProduct());
            dto.setPrice(product.getPrice());
            dto.setCompetitivePrice(product.getCompetitivePrice());
            dto.setAddAt(product.getAddAt());
            dto.setShortDescription(product.getShortDescription());
            dto.setDetailDescription(product.getDetailDescription());
            dto.setGender(product.getGender());
            dto.setActiveFlag(product.getActiveFlag());
            dto.setImages(images);
            dto.setSizes(sizes);
            dto.setColors(colors);
            dto.setComments(comments);
            dto.setDiscounts(discounts);
            dto.setTags(tags);
            return dto;
        }
        return null;
    }

    public static PageProductDTO covertToPageDTO(Page<Product> list){
        if(list!=null) {
            Collection<ProductDTO> products = list.stream().map(ProductConverter::covertToDTO).collect(Collectors.toList());
            PageDTO page = new PageDTO(list.getTotalPages(), products.size(), list.isFirst(), list.isLast());
            return new PageProductDTO(products,page);
        }
        return null;
    }

    public static Product covertProductFormToProduct(ProductFormDTO dto){
        if(dto!=null){
            Product product = new Product();
            product.setId(dto.getId());
            product.setCode(dto.getCode());
            product.setName(dto.getName());
            product.setShortDescription(dto.getShortDescription());
            product.setDetailDescription(dto.getDetailDescription());
            product.setPrice(dto.getPrice());
            product.setCompetitivePrice(dto.getCompetitivePrice());
            product.setGender(dto.getGender());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            product.setAddAt(Timestamp.valueOf(sdf.format(new Date())));
            product.setActiveFlag(dto.getActiveFlag());
            product.setNewProduct(false);
            return product;
        }
        return null;
    }
}
