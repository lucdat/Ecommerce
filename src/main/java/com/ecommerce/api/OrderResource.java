package com.ecommerce.api;

import com.ecommerce.domain.OrderStatus;
import com.ecommerce.dto.domain.OrderItemDTO;
import com.ecommerce.dto.domain.PageOrderDTO;
import com.ecommerce.service.OrderService;
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
public class OrderResource {
    private final OrderService orderService;

    @Operation(summary = "Get list orders", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/orders")
    public ResponseEntity<PageOrderDTO> findAll(@RequestParam(required = false,defaultValue = "PENDING")String status,
                                                @RequestParam(required = false,defaultValue = "1") int page,
                                                @RequestParam(required = false,defaultValue = "10") int size){
        return  ResponseEntity.ok().body(orderService.findAll(page,size,status));
    }
    @Operation(summary = "Get list order items", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/order/{id}/items")
    public ResponseEntity<Collection<OrderItemDTO>> getListItems(@PathVariable("id") long id){
        return  ResponseEntity.ok().body(orderService.getListItems(id));
    }

    @Operation(summary = "Update order status", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "/order/{id}/status/{status}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> updateStatus(@PathVariable("id") long id,
                                            @PathVariable("status") String status){
        return ResponseEntity.ok().body(orderService.updateStatus(id,status));
    }
}
