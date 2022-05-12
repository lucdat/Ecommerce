package com.ecommerce.service.impl;

import com.ecommerce.domain.Discount;
import com.ecommerce.domain.Product;
import com.ecommerce.dto.domain.CartDTO;
import com.ecommerce.dto.domain.OrderItemDTO;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repositories.DiscountRepo;
import com.ecommerce.repositories.ProductRepo;
import com.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@SessionScope
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final ProductRepo productRepo;
    private final DiscountRepo discountRepo;
    private Map<String, OrderItemDTO> ORDER_ITEM = new HashMap<>();

    @Override
    public String add(CartDTO cartDTO) {
        Product product = productRepo.findById(cartDTO.getProductId()).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Product ID %s not found",cartDTO.getProductId())));
        Collection<Discount> discounts = discountRepo.getSizeByProductId(product.getId());
        Double sale =0.0;
        for(Discount discount:discounts){
            sale+= discount.getSale();
        }
        if(ORDER_ITEM.containsKey(cartDTO.key())){
            OrderItemDTO orderItem = ORDER_ITEM.get(cartDTO.key());
            orderItem.setQuantity(cartDTO.getQuantity()+ orderItem.getQuantity());
            if(orderItem.getSale()>0){
                orderItem.setTotalPrice(orderItem.getPrice()*orderItem.getQuantity()* orderItem.getSale());
            }else orderItem.setTotalPrice(orderItem.getPrice()*orderItem.getQuantity());
            ORDER_ITEM.put(cartDTO.key(), orderItem);
        }else{
            OrderItemDTO orderItem = new OrderItemDTO();
            orderItem.setProductId(product.getId());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(cartDTO.getQuantity());
            orderItem.setName(product.getName());
            orderItem.setColor(cartDTO.getColor());
            orderItem.setSize(cartDTO.getSize());
            orderItem.setGender(cartDTO.getGender());
            orderItem.setSale(sale);
            if(sale>0){
                orderItem.setTotalPrice(orderItem.getPrice()*orderItem.getQuantity()* orderItem.getSale());
            }else{
                orderItem.setTotalPrice(orderItem.getPrice()*orderItem.getQuantity());
            }
            ORDER_ITEM.put(cartDTO.key(), orderItem);
        }
        return "success";
    }

    @Override
    public String delete(CartDTO cartDTO) {
        if(ORDER_ITEM.containsKey(cartDTO.key())){
            ORDER_ITEM.remove(cartDTO.key());
            return "success";
        }
        return "error";
    }

    @Override
    public OrderItemDTO update(CartDTO cartDTO) {
        if(ORDER_ITEM.containsKey(cartDTO.key())){
            OrderItemDTO orderItem = ORDER_ITEM.get(cartDTO.key());
            orderItem.setQuantity(cartDTO.getQuantity());
            if(orderItem.getSale()>0){
                orderItem.setTotalPrice(orderItem.getPrice()*orderItem.getQuantity()* orderItem.getSale());
            }else orderItem.setTotalPrice(orderItem.getPrice()*orderItem.getQuantity());
            ORDER_ITEM.put(cartDTO.key(), orderItem);
            return orderItem;
        }
        return null;
    }

    @Override
    public int amount() {
        int sum =0;
        for (OrderItemDTO orderItem : ORDER_ITEM.values()) {
            sum += orderItem.getQuantity();
        }
        return sum;
    }

    @Override
    public Double totalPrice() {
        Double totalPrice = 0.0;
        for(OrderItemDTO orderItem : ORDER_ITEM.values()){
            totalPrice+= orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    @Override
    public String clear() {
        ORDER_ITEM.clear();
        return "success";
    }

    @Override
    public Collection<OrderItemDTO> orderItem() {
        return ORDER_ITEM.values();
    }
}
