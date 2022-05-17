package com.ecommerce.repositories;

import com.ecommerce.domain.OrderItem;
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
    @Query(value = "FROM Orders as o where year(o.date) = ?1 AND month(o.date)=?2 AND o.status=?3")
    Collection<Orders> gitListOrdersByMonTh(int year,int month,OrderStatus status);
    @Query(value = "FROM Orders as o where year(o.date) = ?1 AND month(o.date)=?2 AND day(o.date)=?3 AND o.status=?4")
    Collection<Orders> gitListOrdersByDay(int year,int month,int day,OrderStatus status);
    @Query(value = "FROM Orders as o where year(o.date) = ?1 AND o.status=?2")
    Collection<Orders> gitListOrdersByYear(int year,OrderStatus status);

    @Query(value = "select i FROM  Orders o join o.orderItems i where year(o.date) = ?1 AND month(o.date)=?2 AND o.status=?3 AND i.id.productId =?4 ")
    Collection<OrderItem> gitListOrdersItemByMonTh(int year, int month, OrderStatus status, Long productId);
    @Query(value = "select i FROM  Orders o join o.orderItems i where year(o.date) = ?1 AND o.status=?2 AND i.id.productId =?3")
    Collection<OrderItem> gitListOrdersItemByYear(int year, OrderStatus status, Long productId);
    @Query(value = "select i FROM  Orders o join o.orderItems i where year(o.date) = ?1 AND month(o.date)=?2 AND day(o.date)=?3 AND o.status=?4 AND i.id.productId =?5 ")
    Collection<OrderItem> gitListOrdersItemByDay(int year, int month,int day, OrderStatus status, Long productId);

}
