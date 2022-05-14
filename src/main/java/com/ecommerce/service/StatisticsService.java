package com.ecommerce.service;

import com.ecommerce.dto.domain.StatisticsFilter;
import com.ecommerce.dto.domain.ProductStatistics;

import java.util.List;

public interface StatisticsService {

    List<ProductStatistics> orderFilter(StatisticsFilter statisticsFilter);
}
