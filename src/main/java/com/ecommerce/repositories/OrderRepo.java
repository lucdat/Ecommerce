package com.ecommerce.repositories;

import com.ecommerce.domain.OrderStatus;
import com.ecommerce.domain.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

public interface OrderRepo extends JpaRepository<Orders,Long> {
    @Query("select o from User u join u.orders o where u.id = ?1")
    Page<Orders> getOrdersByUserId(Long id, Pageable pageable);
    Page<Orders> getOrdersByStatus(OrderStatus status, Pageable pageable);
    Page<Orders> findByStatusAndDateBetween(OrderStatus status,Date dateStart,Date endDate, Pageable pageable);
    @Query(value = "select * FROM Orders as o " +
            "where" +
            " (SELECT EXTRACT(YEAR FROM Timestamp 'o.\"date\"')) = (SELECT EXTRACT(YEAR FROM Timestamp ':eventDate')) " +
            "AND" +
            " (SELECT EXTRACT(MONTH FROM Timestamp 'o.\"date\"')) = (SELECT EXTRACT(MONTH FROM Timestamp ':eventDate')) " +
            "AND" +
            " o.status = :status",nativeQuery = true)
    Collection<Orders> gitListOrdersByMonTh(@Param("eventDate") Timestamp eventDate,@Param("status")OrderStatus status);

}
