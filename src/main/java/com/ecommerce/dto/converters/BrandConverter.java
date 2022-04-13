package com.ecommerce.dto.converters;

import com.ecommerce.domain.Brand;
import com.ecommerce.dto.domain.BrandDTO;
import com.ecommerce.dto.domain.PageBrandDTO;
import com.ecommerce.dto.domain.PageDTO;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class BrandConverter {
    public static BrandDTO covertToDTO(Brand brand){
        if(brand!=null){
            BrandDTO brandDTO = new BrandDTO();
            brandDTO.setId(brand.getId());
            brandDTO.setName(brand.getName());
            brandDTO.setLogo(brand.getLogo());
            brandDTO.setCreateAt(brand.getCreateAt());
            return brandDTO;
        }
        return null;
    }

    public static PageBrandDTO covertToPageBrand(Page<Brand> list){
        if(list!=null){
            PageBrandDTO pageBrand = new PageBrandDTO();
            Collection<BrandDTO> brands = list.stream().map(BrandConverter::covertToDTO).collect(Collectors.toList());
            PageDTO page = new PageDTO(list.getTotalPages(), brands.size(), list.isFirst(), list.isLast());
            pageBrand.setBrands(brands);
            pageBrand.setPage(page);
            return pageBrand;
        }
        return null;
    }

    public static Brand covertToBrand(BrandDTO brandDTO){
        if(brandDTO!=null){
            Brand brand = new Brand();
            brand.setId(brandDTO.getId());
            brand.setName(brandDTO.getName());
            brand.setLogo(brandDTO.getLogo());
            Date date = new Date(System.currentTimeMillis());
            brand.setCreateAt(date);
            return brand;
        }
        return null;
    }
}
