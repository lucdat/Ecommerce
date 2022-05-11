package com.ecommerce.api;

import com.ecommerce.dto.domain.CategoryDTO;
import com.ecommerce.dto.domain.PageCategoryDTO;
import com.ecommerce.dto.domain.PageProductDTO;
import com.ecommerce.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryResource {
    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<PageCategoryDTO> findAll(@RequestParam(required = false,defaultValue = "1") int page,
                                                   @RequestParam(required = false,defaultValue = "10") int size){
        return  ResponseEntity.ok().body(categoryService.findAll(page,size));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable("id") long id){
        return ResponseEntity.ok().body(categoryService.findById(id));
    }
    @Operation(summary = "Create category", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/category",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> saveCategory(@Valid @RequestBody CategoryDTO category){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/api/v1/category").toString());
        return ResponseEntity.created(uri).body(categoryService.save(category));
    }
    @Operation(summary = "Update category", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "/category/update",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO category){
        return ResponseEntity.ok().body(categoryService.update(category));
    }
    @Operation(summary = "Add product to category", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/category/{cateId}/add/product/{productId}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProductToCategory(@PathVariable("cateId") Long cateId,
                                                            @PathVariable("productId") Long productId){
        return ResponseEntity.ok().body(categoryService.addProduct(cateId,productId));
    }
    @Operation(summary = "Remove product in category", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/category/{cateId}/remove/product/{productId}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeProduct(@PathVariable("cateId") Long cateId,
                                                       @PathVariable("productId") Long productId){
        return ResponseEntity.ok().body(categoryService.removeProduct(cateId,productId));
    }
    @GetMapping("/category/{cateId}/products")
    public ResponseEntity<PageProductDTO> getListProduct(@PathVariable("cateId") Long cateId,
                                                         @RequestParam(required = false,defaultValue = "1") int page,
                                                         @RequestParam(required = false,defaultValue = "10") int size){
        return ResponseEntity.ok().body(categoryService.getListProducts(cateId,page,size));
    }
}
