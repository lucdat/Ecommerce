package com.ecommerce.api;

import com.ecommerce.dto.domain.ImportFilter;
import com.ecommerce.dto.domain.OrderFilter;
import com.ecommerce.dto.domain.ProductFilter;
import com.ecommerce.dto.domain.Statistics;
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

@RestController
@RequestMapping("api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsResource {
    public final StatisticsService service;
    @Operation(summary = "order statistics,date format dd/mm/yyyy,type 1 month,2 year,other day", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("order")
    public ResponseEntity<Statistics> getStatisticsOrders(@Valid @RequestBody OrderFilter orderFilter){
        return ResponseEntity.ok().body(service.orderFilter(orderFilter));
    }
    @Operation(summary = "import statistics,date format dd/mm/yyyy,type 1 month,2 year,other day", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("import")
    public ResponseEntity<Statistics> getStatisticsImport(@Valid @RequestBody ImportFilter importFilter){
        return ResponseEntity.ok().body(service.importFilter(importFilter));
    }
    @Operation(summary = "product statistics,date format dd/mm/yyyy,type 1 month,2 year,other day", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("product")
    public ResponseEntity<Statistics> getStatisticsProduct(@Valid @RequestBody ProductFilter productFilter){
        return ResponseEntity.ok().body(service.productFilter(productFilter));
    }
}
