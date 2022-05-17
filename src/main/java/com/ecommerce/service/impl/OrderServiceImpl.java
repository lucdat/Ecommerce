package com.ecommerce.service.impl;

import com.ecommerce.domain.*;
import com.ecommerce.dto.converters.OrderConverter;
import com.ecommerce.dto.converters.OrderItemConverter;
import com.ecommerce.dto.domain.OrderDTO;
import com.ecommerce.dto.domain.OrderItemDTO;
import com.ecommerce.dto.domain.PageOrderDTO;
import com.ecommerce.exception.OrderStatusException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.generators.OrderItemFK;
import com.ecommerce.repositories.*;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final SizeRepo sizeRepo;
    private final ColorRepo colorRepo;
    private final ProductInStockRepo productInStockRepo;
    @Override
    public PageOrderDTO findAll(int page, int size,String status) {
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Orders> orders;
        if(status.equals(OrderStatus.PENDING.toString())
                ||status.equals(OrderStatus.PROCESSING.toString())
                ||status.equals(OrderStatus.DELIVERED.toString())
                ||status.equals(OrderStatus.DELIVERED.toString())){
            orders = orderRepo.getOrdersByStatus(OrderStatus.valueOf(status),pageable);
        }else orders = orderRepo.findAll(pageable);
        return OrderConverter.convertToPageDTO(orders);
    }

    @Override
    public Map<String,String> checkOut(CartService cartService, OrderDTO orderDTO) {
        Map<String,String> response = new HashMap<>();
        //Create order
        Orders order = OrderConverter.convertDtoToEntity(orderDTO);
        order.setAmount(cartService.amount());
        order.setTotalPrice(cartService.totalPrice());
        order.setAmount(cartService.amount());
        order.setTotalPrice(cartService.totalPrice());
        order.setDate(new Date());
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
        response.put("message","success");
        return response;
    }

    @Override
    public Collection<OrderItemDTO> getListItems(long id) {
        Collection<OrderItem> orderItems = orderItemRepo.findByOrderId(id);
        return orderItems.stream().map(OrderItemConverter::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Map<String,String> updateStatus(long id, String status) {
        Orders order = orderRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Order ID %s not found",id)));
        Map<String,String> response;
        switch (status){
            case "PENDING":{
                response = orderPending(order);
                return response;
            }
            case "PROCESSING":{
                response = orderProcessing(order);
                if (response.isEmpty()) {
                    response = new HashMap<>();
                    response.put("message", "success");
                }
                return response;
            }
            case "DELIVERED":{
                response = new HashMap<>();
                response.put("message","Unable to change the status of the order delivered");
                return response;
            }
            case "CANCEL":{
                response = orderCancel(order);
                return response;
            }
            default:throw new OrderStatusException(String.format("Status %s not valid",status));
        }
    }

    private Map<String,String> orderCancel(Orders order) {
        Map<String,String> response = new HashMap<>();
        if(order.getStatus().equals(OrderStatus.PENDING) || order.getStatus().equals(OrderStatus.PROCESSING) ){
            Collection<OrderItem> orderItems = orderItemRepo.findByOrderId(order.getId());
            if(order.getStatus().equals(OrderStatus.PROCESSING)){
                for(OrderItem item : orderItems){
                    Color color = colorRepo.findByColor(item.getColor());
                    Size size = sizeRepo.findBySize(item.getSize());
                    Product product = productRepo.getById(item.getId().getProductId());
                    List<ProductInStock> productInStock = productInStockRepo
                            .findByIdColorIdAndIdSizeIdAndIdProductIdAndIdGender(color.getId(),size.getId(),item.getId().getProductId(), product.getGender());
                    productInStock.get(0).setQuantity(productInStock.get(0).getQuantity() + item.getQuantity());
                    product.setQuantity(product.getQuantity() + item.getQuantity());
                }
                response = new HashMap<>();
                response.put("message","update status success");
                order.setStatus(OrderStatus.CANCEL);
                orderRepo.save(order);
            }
        }else{
            response = new HashMap<>();
            response.put("message","Unable to change the status of the order");
        }
        return response;
    }

    private Map<String,String> orderProcessing(Orders order) {
        Collection<OrderItem> orderItems = orderItemRepo.findByOrderId(order.getId());
        boolean check = true;
        Map<String,String> response = new HashMap<>();
        List<ProductInStock> productInStock = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        for(OrderItem item : orderItems){
            Color color = colorRepo.findByColor(item.getColor());
            Size size = sizeRepo.findBySize(item.getSize());
            Product product = productRepo.getById(item.getId().getProductId());
            productInStock = productInStockRepo
                    .findByIdColorIdAndIdSizeIdAndIdProductIdAndIdGender(color.getId(),size.getId(),item.getId().getProductId(), item.getGender());
            if(!productInStock.isEmpty() && productInStock.get(0).getQuantity() < item.getQuantity()){
                check = false;
                response.put(product.getName(),"Quantity is not enough");
            }else if(!productInStock.isEmpty() && productInStock.get(0).getQuantity() > item.getQuantity()){
                productInStock.get(0).setQuantity(productInStock.get(0).getQuantity() - item.getQuantity());
                productInStock.add(productInStock.get(0));
                product.setQuantity(product.getQuantity() - item.getQuantity());
                products.add(product);
            }
        }
        if(check){
            for(Product product : products){
                productRepo.save(product);
            }
            for(ProductInStock inStock : productInStock){
                productInStockRepo.save(inStock);
            }
            order.setStatus(OrderStatus.DELIVERED);
            orderRepo.save(order);
        }
        return response;
    }

    private Map<String,String> orderPending(Orders order){
        order.setStatus(OrderStatus.PROCESSING);
        orderRepo.save(order);
        Map<String,String> response = new HashMap<>();
        response.put("message","update status success");
        return response;
    }
}
