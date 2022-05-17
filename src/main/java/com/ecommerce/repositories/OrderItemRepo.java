package com.ecommerce.repositories;

import com.ecommerce.domain.OrderItem;
import com.ecommerce.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;


public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {
    Collection<OrderItem> findByOrderId(long id);

}
