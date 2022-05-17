package com.ecommerce.repositories;

import com.ecommerce.domain.Import;
import com.ecommerce.domain.OrderStatus;
import com.ecommerce.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ImportRepo extends JpaRepository<Import,Long> {
    @Query(value = "FROM Orders as o where year(o.date) = ?1 AND month(o.date)=?2")
    Collection<Import> gitListOrdersByMonTh(int year, int month);
    @Query(value = "FROM Orders as o where year(o.date) = ?1 AND month(o.date)=?2 AND day(o.date)=?3")
    Collection<Import> gitListOrdersByDay(int year,int month,int day);
    @Query(value = "FROM Orders as o where year(o.date) = ?1")
    Collection<Import> gitListOrdersByYear(int year);
}
