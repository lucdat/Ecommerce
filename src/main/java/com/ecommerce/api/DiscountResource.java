package com.ecommerce.api;

import com.ecommerce.dto.domain.*;
import com.ecommerce.service.DiscountService;
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
    @PostMapping(value = "/discount",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DiscountDTO> saveBrand(@Valid @RequestBody DiscountDTO discountDTO){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/api/v1/discount").toString());
        return ResponseEntity.created(uri).body(discountService.save(discountDTO));
    }
    @PutMapping("/discount/update")
    public ResponseEntity<DiscountDTO> updateCategory(@Valid @RequestBody DiscountDTO discountDTO){
        return ResponseEntity.ok().body(discountService.update(discountDTO));
    }
    @PostMapping(value = "/discount/{discountId}/product/{productId}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProductToBrand(@PathVariable("discountId") Long discountId,
                                                    @PathVariable("productId") Long productId){
        return ResponseEntity.ok().body(discountService.addProduct(discountId,productId));
    }
    @PostMapping(value = "/discount/{discountId}/remove/product/{productId}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeProduct(@PathVariable("discountId") Long discountId,
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
