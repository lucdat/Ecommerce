package com.ecommerce.repositories;

import com.ecommerce.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;


public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {
    Collection<OrderItem> findByOrderId(long id);
}
