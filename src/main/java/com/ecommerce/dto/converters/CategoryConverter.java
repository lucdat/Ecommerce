package com.ecommerce.dto.converters;


import com.ecommerce.domain.Category;
import com.ecommerce.dto.domain.CategoryDTO;
import com.ecommerce.dto.domain.PageCategoryDTO;
import com.ecommerce.dto.domain.PageDTO;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class CategoryConverter {
    public static CategoryDTO covertToDTO(Category category){
        if(category!=null){
            CategoryDTO dto = new CategoryDTO();
            dto.setId(category.getId());
            dto.setName(category.getName());
            return dto;
        }
        return null;
    }
    public static PageCategoryDTO covertToPageDTO(Page<Category> list){
        if(list!=null){
           Collection<CategoryDTO> categories=list.stream().map(CategoryConverter::covertToDTO).collect(Collectors.toList());
           PageDTO page = new PageDTO(list.getTotalPages(), categories.size(), list.isFirst(), list.isLast());
            return new PageCategoryDTO(categories,page);
        }
        return null;
    }

    public static Category covertToCategory(CategoryDTO dto){
        if(dto!=null){
            Date date = new Date(System.currentTimeMillis());
            Category category = new Category();
            category.setName(dto.getName());
            category.setCreateAt(date);
            return category;
        }
        return null;
    }
}
