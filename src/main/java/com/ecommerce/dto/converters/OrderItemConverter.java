package com.ecommerce.dto.converters;

import com.ecommerce.domain.OrderItem;
import com.ecommerce.dto.domain.OrderItemDTO;

public class OrderItemConverter {
    public static OrderItemDTO convertToDTO(OrderItem orderItem){
          if(orderItem!=null){
              OrderItemDTO dto = new OrderItemDTO();
              dto.setId(orderItem.getId());
              dto.setName(orderItem.getName());
              dto.setSize(orderItem.getSize());
              dto.setGender(orderItem.getGender());
              dto.setColor(orderItem.getColor());
              dto.setPrice(orderItem.getTotalPrice()/ orderItem.getQuantity());
              dto.setQuantity(orderItem.getQuantity());
              dto.setTotalPrice(orderItem.getTotalPrice());
              return dto;
          }
        return null;
    }

    public static OrderItem convertDtoToEntity(OrderItemDTO item){
        if(item!=null){
            OrderItem orderItem = new OrderItem();
            orderItem.setColor(item.getColor());
            orderItem.setSize(item.getSize());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(item.getTotalPrice());
            orderItem.setName(item.getName());
            return orderItem;
        }
        return null;
    }
}
