package com.ecommerce.service;

import com.ecommerce.dto.domain.CartDTO;
import com.ecommerce.dto.domain.OrderItemDTO;

import java.util.Collection;

public interface CartService {
    String add(CartDTO cartDTO);
    String delete(CartDTO cartDTO);
    OrderItemDTO update(CartDTO cartDTO);
    int amount();
    Double totalPrice();
    String clear();
    Collection<OrderItemDTO> orderItem();
}
