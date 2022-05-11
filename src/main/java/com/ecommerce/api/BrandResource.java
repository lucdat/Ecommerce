package com.ecommerce.api;

import com.ecommerce.dto.domain.BrandDTO;
import com.ecommerce.dto.domain.PageBrandDTO;
import com.ecommerce.dto.domain.PageProductDTO;
import com.ecommerce.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class BrandResource {
    private final BrandService brandService;

    @GetMapping("/brands")
    public ResponseEntity<PageBrandDTO> findAll(@RequestParam(required = false,defaultValue = "1") int page,
                                                @RequestParam(required = false,defaultValue = "10") int size){
        return  ResponseEntity.ok().body(brandService.findAll(page,size));
    }
    @GetMapping("brand/{id}")
    public ResponseEntity<BrandDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(brandService.findById(id));
    }
    @Operation(summary = "Create brand", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/brand",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BrandDTO> saveBrand(@Valid @RequestBody BrandDTO brandDTO){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/api/v1/brand").toString());
        return ResponseEntity.created(uri).body(brandService.save(brandDTO));
    }
    @Operation(summary = "Update brand", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/brand/update")
    public ResponseEntity<BrandDTO> updateCategory(@Valid @RequestBody BrandDTO brandDTO){
        return ResponseEntity.ok().body(brandService.update(brandDTO));
    }
    @Operation(summary = "Add product to brand", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/brand/{brandId}/add/product/{productId}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProductToBrand(@PathVariable("brandId") Long brandId,
                                                       @PathVariable("productId") Long productId){
        return ResponseEntity.ok().body(brandService.addProduct(brandId,productId));
    }
    @Operation(summary = "Remove product in brand", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/brand/{brandId}/remove/product/{productId}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeProduct(@PathVariable("brandId") Long brandId,
                                                       @PathVariable("productId") Long productId){
        return ResponseEntity.ok().body(brandService.removeProduct(brandId,productId));
    }
    @GetMapping("/brand/{brandId}/products")
    public ResponseEntity<PageProductDTO> getListProduct(@PathVariable("brandId") Long brandId,
                                                         @RequestParam(required = false,defaultValue = "1") int page,
                                                         @RequestParam(required = false,defaultValue = "10") int size){
        return ResponseEntity.ok().body(brandService.getListProducts(brandId,page,size));
    }
    @Operation(summary = "Upload logo", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value="/brand/{id}/uploadLogo",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadImages(@PathVariable("id")Long brandId, @RequestParam("logo") MultipartFile image){
        return ResponseEntity.ok().body(brandService.uploadLogo(brandId,image));
    }
}
