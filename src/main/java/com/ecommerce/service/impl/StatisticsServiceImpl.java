package com.ecommerce.service.impl;


import com.ecommerce.domain.OrderStatus;
import com.ecommerce.domain.Orders;
import com.ecommerce.dto.converters.OrderConverter;
import com.ecommerce.dto.domain.OrderFilter;
import com.ecommerce.dto.domain.PageOrderDTO;
import com.ecommerce.repositories.OrderRepo;
import com.ecommerce.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final OrderRepo orderRepo;
    @Override
    public PageOrderDTO orderFilter(OrderFilter orderFilter) {
        Pageable pageable = PageRequest.of(orderFilter.getPage()-1, orderFilter.getSize());
        Page<Orders> orders =
                orderRepo.findByStatusAndDateBetween(OrderStatus.valueOf(orderFilter.getStatus()),orderFilter.getDateStart(),orderFilter.getDateEnd(),pageable);
        return OrderConverter.convertToPageDTO(orders);
    }
}
