package com.ecommerce.api;

import com.ecommerce.dto.domain.PageProductDTO;
import com.ecommerce.dto.domain.PageTagDTO;
import com.ecommerce.dto.domain.TagDTO;
import com.ecommerce.service.TagService;
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
public class TagResource {
    private final TagService tagService;

    @GetMapping("/tags")
    public ResponseEntity<PageTagDTO> findAll(@RequestParam(required = false,defaultValue = "1") int page,
                                              @RequestParam(required = false,defaultValue = "10") int size){
        return  ResponseEntity.ok().body(tagService.findAll(page,size));
    }
    @PostMapping(value = "/tag",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TagDTO> saveTag(@Valid @RequestBody TagDTO tagDTO){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/api/v1/tag").toString());
        return ResponseEntity.created(uri).body(tagService.save(tagDTO));
    }
    @PutMapping(value = "/tag/update",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TagDTO> updateTag(@Valid @RequestBody TagDTO tagDTO){
        return ResponseEntity.ok().body(tagService.update(tagDTO));
    }

    @PostMapping(value = "/tag/{tagId}/product/{productId}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProductToTag(@PathVariable("tagId") Long tagId,
                                                       @PathVariable("productId") Long productId){
        return ResponseEntity.ok().body(tagService.addProduct(tagId,productId));
    }
    @PostMapping(value = "/tag/{tagId}/remove/product/{productId}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeProduct(@PathVariable("tagId") Long tagId,
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
