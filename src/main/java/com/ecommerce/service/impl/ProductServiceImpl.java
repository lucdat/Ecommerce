package com.ecommerce.service.impl;

import com.ecommerce.domain.*;
import com.ecommerce.dto.converters.ProductConverter;
import com.ecommerce.dto.domain.CommentDTO;
import com.ecommerce.dto.domain.PageProductDTO;
import com.ecommerce.dto.domain.ProductDTO;
import com.ecommerce.dto.domain.ProductFormDTO;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UniqueConstrainException;
import com.ecommerce.generators.CommentFK;
import com.ecommerce.repositories.*;
import com.ecommerce.service.FileStorageService;
import com.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final ImageRepo imageRepo;
    private final SizeRepo sizeRepo;
    private final ColorRepo colorRepo;
    private final DiscountRepo discountRepo;
    private final TagRepo tagRepo;
    private final UserRepo userRepo;
    private final CommentRepo commentRepo;
    private final FileStorageService fileStorageService;

    @Override
    public PageProductDTO findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size, Sort.by(Sort.Direction.ASC,"price"));
        Page<Product> products = productRepo.findByActiveFlagIs(true,pageable);
        products.forEach(product -> {
            product.setImages(imageRepo.findByProductId(product.getId()));
            product.setSizes(sizeRepo.getSizeByProductId(product.getId()));
            product.setColors(colorRepo.getColorByProductId(product.getId()));
            product.setDiscounts(discountRepo.getSizeByProductId(product.getId()));
            product.setTags(tagRepo.getSizeByProductId(product.getId()));
        });
        return ProductConverter.covertToPageDTO(products);
    }

    @Override
    public ProductDTO findById(Long id) {
        Optional<Product> product = productRepo.findById(id);
        return ProductConverter.covertToDTO(product.get());
    }

    @Override
    public ProductDTO save(ProductFormDTO productDTO) {
        Product product = ProductConverter.covertProductFormToProduct(productDTO);
        Product p = productRepo.findByCode(productDTO.getCode());
        if(p!=null)
            throw new UniqueConstrainException("code:Code already exists!");
        Product productSave = productRepo.save(product);
        if(productDTO.getSizes()!=null){
            for(String s : productDTO.getSizes()){
                Size size = sizeRepo.findBySize(s);
                if(size!=null){
                    productSave.getSizes().add(size);
                    size.getProducts().add(productSave);
                }else{
                    size = new Size();
                    size.setSize(s);
                    Size sizeSave = sizeRepo.save(size);
                    productSave.getSizes().add(sizeSave);
                    sizeSave.getProducts().add(productSave);
                }
            }
        }
        if(productDTO.getColors()!=null){
            for(String c : productDTO.getColors()){
                Color color = colorRepo.findByColor(c);
                if(color!=null){
                    productSave.getColors().add(color);
                    color.getProducts().add(productSave);
                }else{
                    color = new Color();
                    color.setColor(c);
                    Color colorSave = colorRepo.save(color);
                    productSave.getColors().add(color);
                    colorSave.getProducts().add(productSave);
                }
            }
        }
        return ProductConverter.covertToDTO(productSave);
    }

    @Override
    public String update(ProductFormDTO productFormDTO) {
        Product product = productRepo.findById(productFormDTO.getId()).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Product ID %s not found",productFormDTO.getId())));
        if(!product.getCode().equals(productFormDTO.getCode())){
            Product p = productRepo.findByCode(productFormDTO.getCode());
            if(p!=null)
                throw new UniqueConstrainException("code:Code already exists!");
        }
        save(productFormDTO);
        return "successful";
    }

    @Override
    public String comment(Long id, CommentDTO commentDTO) {
        Product product = productRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Product ID %s not found",id)));
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userRepo.findByUsername(username);
        if(user==null)
            throw new ResourceNotFoundException(String.format("%s not found",username));
        Comment comment = new Comment();
        Date date = new Date();
        comment.setDate(date);
        comment.setMessage(commentDTO.getMessage());
        comment.setProduct(product);
        comment.setUser(user);
        comment.setId(new CommentFK(product.getId(), user.getId(),date));
        commentRepo.save(comment);
        user.getComments().add( comment);
        product.getComments().add(comment);
        return null;
    }

    @Override
    public String uploadImages(Long productId, MultipartFile[] images) {
        Product product = productRepo.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Product ID %s not found",productId)));
        for(MultipartFile file:images){
            Image image = new Image();
            String fileName = fileStorageService.storeFile(file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();
            image.setUrl(fileDownloadUri);
            Image saveImage = imageRepo.save(image);
            product.getImages().add(saveImage);
            saveImage.setProduct(product);
        }
        return "successfull";
    }

    @Override
    public PageProductDTO findByNameContaining(String name,int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size, Sort.by(Sort.Direction.ASC,"price"));
        Page<Product> products = productRepo.findByNameContaining(name,pageable);
        products.forEach(product -> {
            product.setImages(imageRepo.findByProductId(product.getId()));
            product.setSizes(sizeRepo.getSizeByProductId(product.getId()));
            product.setColors(colorRepo.getColorByProductId(product.getId()));
            product.setDiscounts(discountRepo.getSizeByProductId(product.getId()));
            product.setTags(tagRepo.getSizeByProductId(product.getId()));
        });
        return ProductConverter.covertToPageDTO(products);
    }

}
