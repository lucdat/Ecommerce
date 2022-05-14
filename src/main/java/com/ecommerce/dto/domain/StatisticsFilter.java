package com.ecommerce.dto.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class StatisticsFilter {
    @NotNull
    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date dateStart;
    @NotNull
    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date dateEnd;
    private String status = "PENDING";
    private Long id;
}
