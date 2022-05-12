package com.ecommerce.api;

import com.ecommerce.dto.domain.OrderFilter;
import com.ecommerce.dto.domain.PageOrderDTO;
import com.ecommerce.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsResource {
    public final StatisticsService service;

    public ResponseEntity<PageOrderDTO> getListOrder(@Valid @RequestBody OrderFilter orderFilter){
        return ResponseEntity.ok().body(service.orderFilter(orderFilter));
    }
}
