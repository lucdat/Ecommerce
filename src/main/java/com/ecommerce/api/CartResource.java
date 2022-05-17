package com.ecommerce.api;

import com.ecommerce.dto.domain.CartDTO;
import com.ecommerce.dto.domain.OrderDTO;
import com.ecommerce.dto.domain.OrderItemDTO;
import com.ecommerce.service.CartService;
import com.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartResource {
    private final CartService cartService;
    private final OrderService orderService;

    @Operation(summary = "Add product to shopping cart", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/cart/add",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> addProductToCartItem(@RequestBody CartDTO cartDTO){
        return ResponseEntity.ok().body(cartService.add(cartDTO));
    }
    @Operation(summary = "Delete product in shopping cart", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/cart/delete",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> deleteProductInCartItem(@RequestBody CartDTO cartDTO){
        return ResponseEntity.ok().body(cartService.delete(cartDTO));
    }
    @Operation(summary = "Update product in shopping cart", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/cart/update",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderItemDTO> updateProductInCartItem(@RequestBody CartDTO cartDTO){
        return ResponseEntity.ok().body(cartService.update(cartDTO));
    }

    @Operation(summary = "Clear shopping cart", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/cart/clear",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> clearCartItem(){
        return ResponseEntity.ok().body(cartService.clear());
    }
    @Operation(summary = "Get list products in shopping cart", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/cart/list")
    public ResponseEntity<Collection<OrderItemDTO>> getListOrderItem(){
        return ResponseEntity.ok().body(cartService.orderItem());
    }

    @Operation(summary = "Checkout shopping cart", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/cart/checkout",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> checkout(@Valid @RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok().body(orderService.checkOut(cartService,orderDTO));
    }
}
