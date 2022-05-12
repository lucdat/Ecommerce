package com.ecommerce.service;

import com.ecommerce.dto.domain.OrderDTO;
import com.ecommerce.dto.domain.OrderFilter;
import com.ecommerce.dto.domain.PageOrderDTO;

import java.util.List;

public interface StatisticsService {

    PageOrderDTO orderFilter(OrderFilter orderFilter);
}
