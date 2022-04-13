package com.ecommerce.repositories;

import com.ecommerce.domain.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface OrderRepo extends JpaRepository<Orders,Long> {
    Collection<Orders> findByPhone(String phone);
    @Query("select o from User u join u.orders o where u.id = ?1")
    Page<Orders> getOrdersByUserId(Long id, Pageable pageable);
}
