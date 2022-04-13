package com.ecommerce.service.impl;

import com.ecommerce.domain.Brand;
import com.ecommerce.domain.Product;
import com.ecommerce.dto.converters.BrandConverter;
import com.ecommerce.dto.converters.ProductConverter;
import com.ecommerce.dto.domain.BrandDTO;
import com.ecommerce.dto.domain.PageBrandDTO;
import com.ecommerce.dto.domain.PageProductDTO;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UniqueConstrainException;
import com.ecommerce.repositories.BrandRepo;
import com.ecommerce.repositories.ProductRepo;
import com.ecommerce.service.BrandService;
import com.ecommerce.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepo brandRepo;
    private final ProductRepo productRepo;
    private final FileStorageService fileStorageService;

    @Override
    public PageBrandDTO findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Brand> brands = brandRepo.findAll(pageable);
        return BrandConverter.covertToPageBrand(brands);
    }

    @Override
    public BrandDTO findById(long id) {
        Brand brand = brandRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Brand ID:%s not found",id)));
        return BrandConverter.covertToDTO(brand);
    }

    @Override
    public BrandDTO save(BrandDTO brandDTO) {
        if(brandRepo.findByName(brandDTO.getName())!=null)
            throw new UniqueConstrainException("name:name already exists");
        Brand brand = brandRepo.save(BrandConverter.covertToBrand(brandDTO));
        return BrandConverter.covertToDTO(brand);
    }

    @Override
    public BrandDTO update(BrandDTO brandDTO) {
        Brand brand = brandRepo.findById(brandDTO.getId()).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Brand ID %s not found",brandDTO.getId())));
        if(!brand.getName().equals(brandDTO.getName())){
            if(brandRepo.findByName(brandDTO.getName())!=null)
                throw new UniqueConstrainException("name:name already exists");
        }
        if(brandDTO.getLogo()!=null) brand.setLogo(brandDTO.getLogo());
        brandRepo.save(brand);
        return BrandConverter.covertToDTO(brand);
    }

    @Override
    public String addProduct(Long brandId, Long productId) {
        if(brandId!=null && productId!=null){
            Brand brand = brandRepo.findById(brandId).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("Brand ID %s not found",brandId)));
            Product product = productRepo.findById(productId).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("Product ID %s not found",productId)));
            brand.getProducts().add(product);
            product.setBrand(brand);
            return "successfull";
        }
        return "error";
    }

    @Override
    public String removeProduct(Long brandId, Long productId) {
        if(brandId!=null && productId!=null){
            Brand brand = brandRepo.findById(brandId).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("Brand ID %s not found",brandId)));
            Product product = productRepo.findById(productId).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("Product ID %s not found",productId)));
            brand.getProducts().remove(product);
            product.setBrand(null);
            return "successfull";
        }
        return "error";
    }

    @Override
    public PageProductDTO getListProducts(Long brandId,int page,int size) {
        if(brandId!=null && !brandRepo.existsById(brandId))
            throw new ResourceNotFoundException(String.format("Brand ID %s not found",brandId));
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Product> products = productRepo.findByBrandIdIs(brandId,pageable);
        return ProductConverter.covertToPageDTO(products);
    }

    @Override
    public String uploadLogo(Long brandId, MultipartFile image) {
        Brand brand = brandRepo.findById(brandId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Brand ID %s not found",brandId)));
        String fileName = fileStorageService.storeFile(image);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        brand.setLogo(fileDownloadUri);
        brandRepo.save(brand);
        return "successfull";
    }
}
