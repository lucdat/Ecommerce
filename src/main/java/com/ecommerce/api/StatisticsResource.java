package com.ecommerce.api;

import com.ecommerce.dto.domain.StatisticsFilter;
import com.ecommerce.dto.domain.ProductStatistics;
import com.ecommerce.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsResource {
    public final StatisticsService service;
//    @Operation(summary = "statistics", security = @SecurityRequirement(name = "bearerAuth"))
//    @PostMapping("product")
//    public ResponseEntity<List<ProductStatistics>> getStatisticsProduct(@Valid @RequestBody StatisticsFilter statisticsFilter){
//        return ResponseEntity.ok().body(service.orderFilter(statisticsFilter));
//    }
}
