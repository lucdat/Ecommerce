package com.ecommerce.service;

import com.ecommerce.dto.domain.CartDTO;
import com.ecommerce.dto.domain.OrderItemDTO;

import java.util.Collection;
import java.util.Map;

public interface CartService {
    Map<String,String> add(CartDTO cartDTO);
    Map<String,String> delete(CartDTO cartDTO);
    OrderItemDTO update(CartDTO cartDTO);
    int amount();
    Double totalPrice();
    Map<String,String> clear();
    Collection<OrderItemDTO> orderItem();
}
