package com.ecommerce.api;

import com.ecommerce.dto.domain.CommentDTO;
import com.ecommerce.dto.domain.PageProductDTO;
import com.ecommerce.dto.domain.ProductDTO;
import com.ecommerce.dto.domain.ProductFormDTO;
import com.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductResource {
    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<PageProductDTO> findAll(@RequestParam(required = false,defaultValue = "1") int activeFlag,
                                                  @RequestParam(required = false,defaultValue = "1") int page,
                                                  @RequestParam(required = false,defaultValue = "10") int size,
                                                  @RequestParam(required = false,defaultValue = "0") int filter){
        return ResponseEntity.ok().body(productService.findAll(activeFlag,page,size,filter));
    }
    @GetMapping("/products/search")
    public ResponseEntity<PageProductDTO> findByNameContaining(@RequestParam(required = false,defaultValue = "1") int page,
                                                               @RequestParam(required = false,defaultValue = "10") int size,
                                                               @RequestParam("name") String name){
        return ResponseEntity.ok().body(productService.findByNameContaining(name,page,size));
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable("id")Long id){
        return ResponseEntity.ok().body(productService.findById(id));
    }
    @Operation(summary = "Create product", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/product",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> saveProduct(@Valid @RequestBody ProductFormDTO productDTO){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/api/v1/product").toString());
        return ResponseEntity.created(uri).body(productService.save(productDTO));
    }
    @Operation(summary = "Update product", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "/product/update",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProduct(@Valid @RequestBody ProductFormDTO productFormDTO){
        return ResponseEntity.ok().body(productService.update(productFormDTO));
    }
    @PostMapping(value = "/product/{id}/comment",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> commentProduct(@PathVariable("id") Long id,
                                                 @RequestBody CommentDTO commentDTO){
        return ResponseEntity.ok().body(productService.comment(id,commentDTO));
    }
    @Operation(summary = "Upload product images", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value="/product/{id}/uploadImages",produces = APPLICATION_JSON_VALUE,consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> uploadImages(@PathVariable("id")Long productId, @RequestParam("images")MultipartFile[] images){
        return ResponseEntity.ok().body(productService.uploadImages(productId,images));
    }
}
