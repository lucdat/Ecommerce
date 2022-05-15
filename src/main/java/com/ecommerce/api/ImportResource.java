package com.ecommerce.api;

import com.ecommerce.dto.domain.*;
import com.ecommerce.generators.ImportItemFK;
import com.ecommerce.service.ImportItemService;
import com.ecommerce.service.ImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ImportResource {
    private final ImportItemService importItemService;
    private final ImportService importService;

    @Operation(summary = "Add item to import", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/import/add",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> addProductToImport(@RequestBody ImportItemDTO importItemDTO){
        return ResponseEntity.ok().body(importItemService.add(importItemDTO));
    }

    @Operation(summary = "Delete item in import", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/import/delete",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> deleteProductInImport(@RequestBody ImportItemDTO importItemDTO){
        return ResponseEntity.ok().body(importItemService.delete(importItemDTO));
    }

    @Operation(summary = "Update  import item", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "/import/update",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> updateProductInImport(@RequestBody ImportItemDTO importItemDTO){
        return ResponseEntity.ok().body(importItemService.update(importItemDTO));
    }
    @Operation(summary = "Clear  import", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/import/clear",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> clearImportItem(){
        return ResponseEntity.ok().body(importItemService.clear());
    }
    @Operation(summary = "Get list import items", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/import/list")
    public ResponseEntity<Collection<ImportItemDTO>> getListItem(){
        return ResponseEntity.ok().body(importItemService.importItem());
    }
    @Operation(summary = "Checkout import", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/import/checkout",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> checkout(){
        return ResponseEntity.ok().body(importService.checkOut(importItemService));
    }

    @Operation(summary = "Get list imports", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/imports",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PageImport> getListImport(@RequestParam(required = false,defaultValue = "1") int page,
                                                    @RequestParam(required = false,defaultValue = "10") int size){
        return ResponseEntity.ok().body(importService.findAll(page,size));
    }

    @Operation(summary = "Get list imports", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/import/{id}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ImportDTO> getImportById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(importService.findById(id));
    }
    @Operation(summary = "Get list imports", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/import/{id}/importItems",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PageImportItem> getListImportItem(@PathVariable("id") Long id,
                                                            @RequestParam(required = false,defaultValue = "1") int page,
                                                            @RequestParam(required = false,defaultValue = "10") int size){
        return ResponseEntity.ok().body(importService.getImportItems(id,page ,size));
    }

    @Operation(summary = "Get list imports", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/importItem",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ImportItemDTO> getImportItem(@RequestBody ImportItemID id){
        return ResponseEntity.ok().body(importService.getImportItem(id));
    }
}
