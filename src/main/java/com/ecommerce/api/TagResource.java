package com.ecommerce.api;

import com.ecommerce.dto.domain.PageProductDTO;
import com.ecommerce.dto.domain.PageTagDTO;
import com.ecommerce.dto.domain.TagDTO;
import com.ecommerce.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TagResource {
    private final TagService tagService;

    @GetMapping("/tags")
    public ResponseEntity<PageTagDTO> findAll(@RequestParam(required = false,defaultValue = "1") int page,
                                              @RequestParam(required = false,defaultValue = "10") int size){
        return  ResponseEntity.ok().body(tagService.findAll(page,size));
    }
    @GetMapping("/tag/{id}")
    public ResponseEntity<TagDTO> findAll(@PathVariable("id") Long id){
        return  ResponseEntity.ok().body(tagService.findById(id));
    }

    @Operation(summary = "Create tag", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/tag",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TagDTO> saveTag(@Valid @RequestBody TagDTO tagDTO){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/api/v1/tag").toString());
        return ResponseEntity.created(uri).body(tagService.save(tagDTO));
    }
    @Operation(summary = "Update tag", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "/tag/update",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TagDTO> updateTag(@Valid @RequestBody TagDTO tagDTO){
        return ResponseEntity.ok().body(tagService.update(tagDTO));
    }

    @Operation(summary = "Add product to tag", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/tag/{tagId}/add/product/{productId}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> addProductToTag(@PathVariable("tagId") Long tagId,
                                                              @PathVariable("productId") Long productId){
        return ResponseEntity.ok().body(tagService.addProduct(tagId,productId));
    }
    @Operation(summary = "Remove product in tag", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/tag/{tagId}/remove/product/{productId}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> removeProduct(@PathVariable("tagId") Long tagId,
                                                @PathVariable("productId") Long productId){
        return ResponseEntity.ok().body(tagService.removeProduct(tagId,productId));
    }
    @GetMapping("/tag/{tagId}/products")
    public ResponseEntity<PageProductDTO> getListProduct(@PathVariable("tagId") Long tagId,
                                                         @RequestParam(required = false,defaultValue = "1") int page,
                                                         @RequestParam(required = false,defaultValue = "10") int size){
        return ResponseEntity.ok().body(tagService.getListProducts(tagId,page,size));
    }
}
