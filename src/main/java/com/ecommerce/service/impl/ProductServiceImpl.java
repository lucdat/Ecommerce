package com.ecommerce.service.impl;

import com.ecommerce.domain.*;
import com.ecommerce.dto.converters.ProductConverter;
import com.ecommerce.dto.domain.CommentDTO;
import com.ecommerce.dto.domain.PageProductDTO;
import com.ecommerce.dto.domain.ProductDTO;
import com.ecommerce.dto.domain.ProductFormDTO;
import com.ecommerce.exception.ImageStorageException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UniqueConstrainException;
import com.ecommerce.generators.CommentFK;
import com.ecommerce.repositories.*;
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

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

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

    @Override
    public PageProductDTO findAll(int activeFlag,int page, int size,int filter) {
        boolean active = activeFlag == 1 ? true : false;
        Pageable pageable = PageRequest.of(page-1,size, Sort.by(Sort.Direction.ASC,"price"));
        Page<Product> products ;
       if(filter == 1 || filter == 2){
           products =productRepo.findByActiveFlagIsAndAndGenderIs(active,filter,pageable);
       }else{
           products =productRepo.findByActiveFlagIs(active,pageable);
       }
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
        product = productRepo.save(product);
        if(productDTO.getSizes()!=null){
            for(String s : productDTO.getSizes()){
                Size size = sizeRepo.findBySize(s);
                if(size==null){
                    size = new Size();
                    size.setSize(s);
                    size = sizeRepo.save(size);
                }
                product.getSizes().add(size);
                size.getProducts().add(product);
            }
        }
        if(productDTO.getColors()!=null){
            for(String c : productDTO.getColors()){
                Color color = colorRepo.findByColor(c);
                if(color==null){
                    color = new Color();
                    color.setColor(c);
                    color = colorRepo.save(color);
                }
                product.getColors().add(color);
                color.getProducts().add(product);
            }
        }
        return ProductConverter.covertToDTO(product);
    }

    @Override
    public Map<String,String> update(ProductFormDTO productFormDTO) {
        Map<String,String> response = new HashMap<>();
        Product product = productRepo.findById(productFormDTO.getId()).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Product ID %s not found",productFormDTO.getId())));
        product.setGender(productFormDTO.getGender());
        product.setShortDescription(productFormDTO.getShortDescription());
        product.setDetailDescription(product.getDetailDescription());
        product.setPrice(productFormDTO.getPrice());
        product.setCompetitivePrice(product.getCompetitivePrice());
        product.setActiveFlag(productFormDTO.getActiveFlag());
        product.setName(productFormDTO.getName());
        product.setNewProduct(productFormDTO.getNewProduct());
        product.setHotProduct(productFormDTO.getHotProduct());
        product.setBestSeller(productFormDTO.getBestSeller());
        if(!product.getCode().equals(productFormDTO.getCode())){
            product.setCode(productFormDTO.getCode());
            Product p = productRepo.findByCode(productFormDTO.getCode());
            if(p!=null )
                throw new UniqueConstrainException("code:Code already exists!");
        }
        productRepo.save(product);
        response.put("message","success");
        return response;
    }

    @Override
    public Map<String,String> comment(Long id, CommentDTO commentDTO) {
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
    public Map<String,String> uploadImages(Long productId, MultipartFile[] images) {
        Map<String,String> response = new HashMap<>();
        Product product = productRepo.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Product ID %s not found",productId)));
        for(MultipartFile file:images){
            try {
                if (file.getOriginalFilename().contains("..")) {
                    throw new ImageStorageException("Image contains invalid path sequence " + file.getOriginalFilename());
                }else if(!file.getOriginalFilename().endsWith(".jpeg")
                        && !file.getOriginalFilename().endsWith(".jpg")
                        && !file.getOriginalFilename().endsWith(".png")){
                    throw new ImageStorageException("Image invalid suffix " + file.getOriginalFilename());
                }
                Image image = new Image();
                image.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
                Image saveImage = imageRepo.save(image);
                product.getImages().add(saveImage);
                saveImage.setProduct(product);
            }catch (IOException exception){
                throw new ImageStorageException(exception.getMessage());
            }

        }
        response.put("message","success");
        return response;
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
