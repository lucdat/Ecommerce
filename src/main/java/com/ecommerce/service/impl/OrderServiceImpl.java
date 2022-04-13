package com.ecommerce.service.impl;

import com.ecommerce.domain.*;
import com.ecommerce.dto.converters.OrderConverter;
import com.ecommerce.dto.converters.OrderItemConverter;
import com.ecommerce.dto.domain.OrderDTO;
import com.ecommerce.dto.domain.OrderItemDTO;
import com.ecommerce.dto.domain.PageOrderDTO;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.generators.OrderItemFK;
import com.ecommerce.repositories.OrderItemRepo;
import com.ecommerce.repositories.OrderRepo;
import com.ecommerce.repositories.ProductRepo;
import com.ecommerce.repositories.UserRepo;
import com.ecommerce.service.CartService;
import com.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;

    @Override
    public PageOrderDTO findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Orders> orders = orderRepo.findAll(pageable);
        return OrderConverter.convertToPageDTO(orders);
    }

    @Override
    public String checkOut(CartService cartService, OrderDTO orderDTO) {
        //Create order
        Orders  order = OrderConverter.convertDtoToEntity(orderDTO);
        order.setAmount(cartService.amount());
        order.setTotalPrice(cartService.totalPrice());
        order.setAmount(cartService.amount());
        order.setTotalPrice(cartService.totalPrice());
        Orders saveOrder = orderRepo.save(order);
        //insert list item to the db
        for(OrderItemDTO item : cartService.orderItem()){
            Product product = productRepo.findById(item.getProductId()).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("Product ID %s not found",item.getId())));
            OrderItem orderItem = OrderItemConverter.convertDtoToEntity(item);
            orderItem.setId(new OrderItemFK(saveOrder.getId(),item.getProductId(),item.getSize(),item.getColor()));
            orderItem.setOrder(saveOrder);
            orderItem.setProduct(product);
            orderItem.setSale(item.getSale());
            OrderItem saveOrderItem = orderItemRepo.save(orderItem);
            //set relationship
            product.getOrderItems().add(saveOrderItem);
            saveOrder.getOrderItems().add(saveOrderItem);
            saveOrderItem.setOrder(saveOrder);
            saveOrderItem.setProduct(product);
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal!=null){
            String username;
            if(principal instanceof UserDetails){
                username = ((UserDetails) principal).getUsername();
            }else{
                username = principal.toString();
            }
            User user = userRepo.findByUsername(username);
            if(user!=null){
                //set user order
                user.getOrders().add(saveOrder);
                saveOrder.setUser(user);
            }
        }
        cartService.clear();
        return "Successfull";
    }

    @Override
    public Collection<OrderItemDTO> getListItems(long id) {
        Collection<OrderItem> orderItems = orderItemRepo.findByOrderId(id);
        return orderItems.stream().map(OrderItemConverter::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public String updateStatus(long id, String status) {
        Orders order = orderRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Order ID %s not found",id)));
        order.setStatus(OrderStatus.valueOf(status));
        return "successfull";
    }
}
