package com.ecommerce.api;

import com.ecommerce.dto.domain.ImportItemDTO;
import com.ecommerce.service.ImportItemService;
import com.ecommerce.service.ImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ImportResource {
    private final ImportItemService importItemService;
    private final ImportService importService;

    @PostMapping(value = "/import/add",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProductToImport(@RequestBody ImportItemDTO importItemDTO){
        return ResponseEntity.ok().body(importItemService.add(importItemDTO));
    }

    @PostMapping(value = "/import/delete",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteProductInImport(@RequestBody ImportItemDTO importItemDTO){
        return ResponseEntity.ok().body(importItemService.delete(importItemDTO));
    }

    @PostMapping(value = "/import/update",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProductInImport(@RequestBody ImportItemDTO importItemDTO){
        return ResponseEntity.ok().body(importItemService.update(importItemDTO));
    }

    @PostMapping(value = "/import/clear",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> clearImportItem(){
        return ResponseEntity.ok().body(importItemService.clear());
    }

    @GetMapping("/import/list")
    public ResponseEntity<Collection<ImportItemDTO>> getListItem(){
        return ResponseEntity.ok().body(importItemService.importItem());
    }

    @PostMapping(value = "/import/checkout",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkout(){
        return ResponseEntity.ok().body(importService.checkOut(importItemService));
    }
}
