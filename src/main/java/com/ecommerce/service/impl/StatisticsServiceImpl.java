package com.ecommerce.service.impl;


import com.ecommerce.domain.OrderItem;
import com.ecommerce.domain.OrderStatus;
import com.ecommerce.domain.Orders;
import com.ecommerce.dto.domain.StatisticsFilter;
import com.ecommerce.dto.domain.ProductStatistics;
import com.ecommerce.repositories.OrderItemRepo;
import com.ecommerce.repositories.OrderRepo;
import com.ecommerce.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final OrderRepo orderRepo;
    @Override
    public List<ProductStatistics> orderFilter(StatisticsFilter statisticsFilter) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Collection<Orders> orders =  orderRepo
               .gitListOrdersByMonTh(Timestamp.valueOf(simpleDateFormat.format(statisticsFilter.getDateStart())), OrderStatus.valueOf(statisticsFilter.getStatus()));
        return orders.stream().map(orderItem -> {
           ProductStatistics statistics = new ProductStatistics();
           statistics.setQuantitySold(orderItem.getAmount());
           statistics.setTotalSellPrice(orderItem.getTotalPrice());
           return statistics;
       }).collect(Collectors.toList());
    }
}
