package com.ecommerce.dto.converters;

import com.ecommerce.domain.OrderStatus;
import com.ecommerce.domain.Orders;
import com.ecommerce.dto.domain.OrderDTO;
import com.ecommerce.dto.domain.PageDTO;
import com.ecommerce.dto.domain.PageOrderDTO;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


public class OrderConverter {
    public static OrderDTO convertToDTO(Orders order){
        if(order!=null){
            OrderDTO dto = new OrderDTO();
            dto.setId(order.getId());
            dto.setName(order.getUser().getName());
            dto.setPhone(order.getUser().getPhone());
            dto.setEmail(order.getUser().getEmail());
            dto.setAddress(order.getAddress());
            dto.setNote(order.getNote());
            dto.setAmount(order.getAmount());
            dto.setStatus(order.getStatus());
            dto.setDate(order.getDate());
            dto.setTotalPrice(order.getTotalPrice());
            return dto;
        }
        return null;
    }
    public static PageOrderDTO convertToPageDTO(Page<Orders> orders){
        if(orders!=null){
            Collection<OrderDTO> dtos = orders.stream().map(OrderConverter::convertToDTO).collect(Collectors.toList());
            PageDTO page = new PageDTO(orders.getTotalPages(), dtos.size(), orders.isFirst(), orders.isLast());
            return new PageOrderDTO(dtos,page);
        }
        return null;
    }

    public static Orders convertDtoToEntity(OrderDTO orderDTO){
            Orders order = new Orders();
            order.setAddress(orderDTO.getAddress());
            order.setNote(orderDTO.getNote());
            order.setStatus(OrderStatus.PENDING);
            order.setDate(new Date());
            return order;
    }
}
