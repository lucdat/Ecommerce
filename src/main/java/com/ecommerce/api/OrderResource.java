package com.ecommerce.api;

import com.ecommerce.dto.domain.OrderItemDTO;
import com.ecommerce.dto.domain.PageOrderDTO;
import com.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderResource {
    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<PageOrderDTO> findAll(@RequestParam(required = false,defaultValue = "1") int page,
                                                @RequestParam(required = false,defaultValue = "10") int size){
        return  ResponseEntity.ok().body(orderService.findAll(page,size));
    }
    @GetMapping("/order/{id}/items")
    public ResponseEntity<Collection<OrderItemDTO>> getListItems(@PathVariable("id") long id){
        return  ResponseEntity.ok().body(orderService.getListItems(id));
    }

    @PutMapping(value = "/order/{id}/status/{status}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStatus(@PathVariable("id") long id,
                                               @PathVariable("status") String status){
        return ResponseEntity.ok().body(orderService.updateStatus(id,status));
    }
}
