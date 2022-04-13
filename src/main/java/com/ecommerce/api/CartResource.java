package com.ecommerce.api;

import com.ecommerce.dto.domain.CartDTO;
import com.ecommerce.dto.domain.OrderDTO;
import com.ecommerce.dto.domain.OrderItemDTO;
import com.ecommerce.service.CartService;
import com.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartResource {
    private final CartService cartService;
    private final OrderService orderService;

    @PostMapping(value = "/cart/add",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProductToCartItem(@RequestBody CartDTO cartDTO){
        return ResponseEntity.ok().body(cartService.add(cartDTO));
    }

    @PostMapping(value = "/cart/delete",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteProductInCartItem(@RequestBody CartDTO cartDTO){
        return ResponseEntity.ok().body(cartService.delete(cartDTO));
    }

    @PostMapping(value = "/cart/update",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderItemDTO> updateProductInCartItem(@RequestBody CartDTO cartDTO){
        return ResponseEntity.ok().body(cartService.update(cartDTO));
    }

    @PostMapping(value = "/cart/clear",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> clearCartItem(){
        return ResponseEntity.ok().body(cartService.clear());
    }

    @GetMapping("/cart/list")
    public ResponseEntity<Collection<OrderItemDTO>> getListOrderItem(){
        return ResponseEntity.ok().body(cartService.orderItem());
    }

    @PostMapping(value = "/cart/checkout",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkout(@Valid @RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok().body(orderService.checkOut(cartService,orderDTO));
    }
}
