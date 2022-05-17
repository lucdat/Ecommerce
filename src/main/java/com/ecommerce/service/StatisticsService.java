package com.ecommerce.service;

import com.ecommerce.dto.domain.ImportFilter;
import com.ecommerce.dto.domain.OrderFilter;
import com.ecommerce.dto.domain.ProductFilter;
import com.ecommerce.dto.domain.Statistics;

public interface StatisticsService {

    Statistics orderFilter(OrderFilter orderFilter);

    Statistics importFilter(ImportFilter importFilter);

    Statistics productFilter(ProductFilter productFilter);
}
