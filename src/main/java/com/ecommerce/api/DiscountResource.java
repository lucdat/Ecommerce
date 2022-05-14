package com.ecommerce.api;

import com.ecommerce.dto.domain.*;
import com.ecommerce.service.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DiscountResource {
    private final DiscountService discountService;

    @GetMapping("/discounts")
    public ResponseEntity<PageDiscountDTO> findAll(@RequestParam(required = false,defaultValue = "1") int page,
                                                   @RequestParam(required = false,defaultValue = "10") int size){
        return  ResponseEntity.ok().body(discountService.findAll(page,size));
    }
    @GetMapping("discount/{id}")
    public ResponseEntity<DiscountDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(discountService.findById(id));
    }
    @Operation(summary = "Create discount", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/discount",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DiscountDTO> createDiscount(@Valid @RequestBody DiscountDTO discountDTO){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/api/v1/discount").toString());
        return ResponseEntity.created(uri).body(discountService.save(discountDTO));
    }
    @Operation(summary = "Update discount", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/discount/update")
    public ResponseEntity<DiscountDTO> updateDiscount(@Valid @RequestBody DiscountDTO discountDTO){
        return ResponseEntity.ok().body(discountService.update(discountDTO));
    }
    @Operation(summary = "Add product to discount", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/discount/{discountId}/add/product/{productId}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> addProduct(@PathVariable("discountId") Long discountId,
                                                                                     @PathVariable("productId") Long productId){
        return ResponseEntity.ok().body(discountService.addProduct(discountId,productId));
    }
    @Operation(summary = "Remove product in discount", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/discount/{discountId}/remove/product/{productId}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity< Map<String,String>> removeProduct(@PathVariable("discountId") Long discountId,
                                                @PathVariable("productId") Long productId){
        return ResponseEntity.ok().body(discountService.removeProduct(discountId,productId));
    }
    @GetMapping("/discount/{discountId}/products")
    public ResponseEntity<PageProductDTO> getListProduct(@PathVariable("discountId") Long discountId,
                                                         @RequestParam(required = false,defaultValue = "1") int page,
                                                         @RequestParam(required = false,defaultValue = "10") int size){
        return ResponseEntity.ok().body(discountService.getListProducts(discountId,page,size));
    }
}
