package com.ecommerce.service.impl;

import com.ecommerce.domain.*;
import com.ecommerce.dto.domain.ImportItemDTO;
import com.ecommerce.generators.ImportItemFK;
import com.ecommerce.generators.ProductInStockFK;
import com.ecommerce.repositories.*;
import com.ecommerce.service.ImportItemService;
import com.ecommerce.service.ImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImportServiceImpl implements ImportService {
    private final ImportRepo importRepo;
    private final ImportItemRepo importItemRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final SizeRepo sizeRepo;
    private final ColorRepo colorRepo;
    private final ProductInStockRepo productInStockRepo;

    @Override
    public String checkOut(ImportItemService importItemService) {
        //insert import to the db
        Import anImport = new Import();
        anImport.setAmount(importItemService.amount());
        anImport.setTotalPrice(importItemService.totalPrice());
        anImport.setDate(new Date());
        Import saveImport = importRepo.save(anImport);
        //Get user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal!=null){
            String username;
            if(principal instanceof UserDetails){
                username = ((UserDetails) principal).getUsername();
            }else{
                username = principal.toString();
            }
            User user = userRepo.findByUsername(username);
            if(user!=null){
                //set relationship
                user.getImports().add(saveImport);
                saveImport.setUser(user);
                //update name in the import
                saveImport.setName(user.getName());
                importRepo.save(saveImport);
            }
        }
        //insert importItem to db
        for(ImportItemDTO dto:importItemService.importItem()){
            Product product = productRepo.findByCode(dto.getCode());
            //if product exist
            if(product!=null){
                //Get list sizes,colors in the product
                Collection<String> sizes = sizeRepo.getSizeByProductId(product.getId())
                        .stream().map(Size::getSize).collect(Collectors.toList());
                Collection<String> colors = colorRepo.getColorByProductId(product.getId())
                        .stream().map(Color::getColor).collect(Collectors.toList());
                Size size = sizeRepo.findBySize(dto.getSize());
                Color color = colorRepo.findByColor(dto.getColor());
                //if size doesn't exist in the product
                if(!sizes.contains(dto.getSize())){
                    //if size doesn't exist in the db
                    if(size==null){
                        size = new Size();
                        size.setSize(dto.getSize());
                        size = sizeRepo.save(size);
                    }
                    product.getSizes().add(size);
                    size.getProducts().add(product);
                }
                //if color doesn't exist in the product
                if(!colors.contains(dto.getColor()))
                    if (color == null) {
                    color = new Color();
                    color.setColor(dto.getColor());
                    color = colorRepo.save(color);
                    color.getProducts().add(product);
                    product.getColors().add(color);
                } else {
                    color.getProducts().add(product);
                    product.getColors().add(color);
                }

                ProductInStockFK productInStockFK = new ProductInStockFK();
                productInStockFK.setColorId(color.getId());
                productInStockFK.setSizeId(color.getId());
                productInStockFK.setProductId(product.getId());
                productInStockFK.setGender(productInStockFK.getGender());
                List<ProductInStock> productInStocks = productInStockRepo
                        .findByIdColorIdAndIdSizeIdAndIdProductIdAndIdGender(productInStockFK.getColorId(), productInStockFK.getSizeId(), productInStockFK.getProductId(), productInStockFK.getGender());
                ProductInStock productInStock;
                if(productInStocks.isEmpty()){
                    productInStock = new ProductInStock();
                    productInStock.setId(productInStockFK);
                    productInStock.setQuantity(dto.getQuantity());
                }else{
                    productInStock = productInStocks.get(0);
                    productInStock.setQuantity(productInStock.getQuantity()+dto.getQuantity());
                }
                productInStockRepo.save(productInStock);
                //update quantity
                product.setQuantity(product.getQuantity()+dto.getQuantity());
                productRepo.save(product);
            }else{
                //create and insert new product
                product = new Product();
                product.setCode(dto.getCode());
                product.setName(dto.getName());
                product.setQuantity(dto.getQuantity());
                product.setPrice(0.0);
                product.setAddAt(new Date());
                product.setActiveFlag(false);
                product.setQuantity(dto.getQuantity());
                product.setGender(dto.getGender());
                product = productRepo.save(product);
                //add color,size to the product
                Size size = sizeRepo.findBySize(dto.getSize());
                Color color = colorRepo.findByColor(dto.getColor());
                //if size doesn't exist in the db
                if(size==null){
                    size = new Size();
                    size.setSize(dto.getSize());
                    size = sizeRepo.save(size);
                }
                product.getSizes().add(size);
                size.getProducts().add(product);
                if(color==null){
                    color = new Color();
                    color.setColor(dto.getColor());
                    color = colorRepo.save(color);
                }
                color.getProducts().add(product);
                product.getColors().add(color);

                ProductInStockFK productInStockFK = new ProductInStockFK();
                productInStockFK.setColorId(color.getId());
                productInStockFK.setSizeId(color.getId());
                productInStockFK.setProductId(product.getId());
                productInStockFK.setGender(product.getGender());
                List<ProductInStock> productInStocks = productInStockRepo
                        .findByIdColorIdAndIdSizeIdAndIdProductIdAndIdGender(productInStockFK.getColorId(), productInStockFK.getSizeId(), productInStockFK.getProductId(),productInStockFK.getGender());
                ProductInStock productInStock;
                if(productInStocks.isEmpty()){
                    productInStock = new ProductInStock();
                    productInStock.setId(productInStockFK);
                    productInStock.setQuantity(dto.getQuantity());
                }else{
                    productInStock = productInStocks.get(0);
                    productInStock.setQuantity(productInStock.getQuantity()+dto.getQuantity());
                }
                productInStockRepo.save(productInStock);
            }
            //insert new importItem to the db
            ImportItem importItem = new ImportItem();
            importItem.setId(new ImportItemFK(saveImport.getId(), product.getId(),dto.getSize(),dto.getColor()));
            importItem.setAnImport(saveImport);
            importItem.setProduct(product);
            importItem.setQuantity(dto.getQuantity());
            importItem.setTotalPrice(dto.getQuantity()*dto.getPrice());
            importItem.setPrice(dto.getPrice());
            importItem.setSize(dto.getSize());
            importItem.setColor(dto.getColor());
            importItem.setGender(dto.getGender());
            ImportItem saveImportItem = importItemRepo.save(importItem);
            //set relationship
            saveImport.getImportItems().add(saveImportItem);
            product.getImportItems().add(saveImportItem);
        }

        return "success";
    }
}
