package com.ecommerce.dto.converters;

import com.ecommerce.domain.Brand;
import com.ecommerce.domain.Discount;
import com.ecommerce.dto.domain.*;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class DiscountConverter {
    public static DiscountDTO covertToDTO(Discount discount){
        if(discount!=null){
            DiscountDTO dto = new DiscountDTO();
            dto.setId(discount.getId());
            dto.setStartDate(discount.getStartDate());
            dto.setEndDate(discount.getEndDate());
            dto.setSale(discount.getSale());
            dto.setActiveFlag(discount.getActiveFlag());
            dto.setDetail(discount.getDetail());
            return dto;
        }
        return null;
    }

    public static PageDiscountDTO covertToPageDiscount(Page<Discount> list){
        if(list!=null){
            Collection<DiscountDTO> dtos =  list.stream().map(DiscountConverter::covertToDTO).collect(Collectors.toList());
            PageDTO page = new PageDTO(list.getTotalPages(),dtos.size(), list.isFirst(), list.isLast());
            return new PageDiscountDTO(dtos,page);
        }
        return null;
    }

    public static Discount covertToDiscount(DiscountDTO dto){
        if(dto!=null){
           Discount discount = new Discount();
            discount.setId(dto.getId());
            discount.setStartDate(dto.getStartDate());
            discount.setEndDate(dto.getEndDate());
            discount.setSale(dto.getSale());
            discount.setActiveFlag(dto.getActiveFlag());
            discount.setDetail(dto.getDetail());
            return discount;
        }
        return null;
    }
}
