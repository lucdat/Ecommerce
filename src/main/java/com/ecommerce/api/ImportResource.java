package com.ecommerce.api;

import com.ecommerce.dto.domain.ImportItemDTO;
import com.ecommerce.service.ImportItemService;
import com.ecommerce.service.ImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(summary = "Add item to import", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/import/add",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProductToImport(@RequestBody ImportItemDTO importItemDTO){
        return ResponseEntity.ok().body(importItemService.add(importItemDTO));
    }

    @Operation(summary = "Delete item in import", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/import/delete",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteProductInImport(@RequestBody ImportItemDTO importItemDTO){
        return ResponseEntity.ok().body(importItemService.delete(importItemDTO));
    }

    @Operation(summary = "Update  import item", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "/import/update",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProductInImport(@RequestBody ImportItemDTO importItemDTO){
        return ResponseEntity.ok().body(importItemService.update(importItemDTO));
    }
    @Operation(summary = "Clear  import", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/import/clear",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> clearImportItem(){
        return ResponseEntity.ok().body(importItemService.clear());
    }
    @Operation(summary = "Get list import items", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/import/list")
    public ResponseEntity<Collection<ImportItemDTO>> getListItem(){
        return ResponseEntity.ok().body(importItemService.importItem());
    }
    @Operation(summary = "Checkout import", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/import/checkout",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkout(){
        return ResponseEntity.ok().body(importService.checkOut(importItemService));
    }
}
