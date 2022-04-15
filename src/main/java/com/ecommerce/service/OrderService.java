package com.ecommerce.service;


import com.ecommerce.dto.domain.OrderDTO;
import com.ecommerce.dto.domain.OrderItemDTO;
import com.ecommerce.dto.domain.PageOrderDTO;

import java.util.Collection;
import java.util.Map;

public interface OrderService {
    PageOrderDTO findAll(int page,int size);
    String checkOut(CartService cartService, OrderDTO orderDTO);
    Collection<OrderItemDTO> getListItems(long id);
    Map<String,String> updateStatus(long id, String status);
}
