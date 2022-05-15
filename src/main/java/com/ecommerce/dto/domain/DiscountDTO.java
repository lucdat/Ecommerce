package com.ecommerce.dto.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {
    private long id;
    @JsonFormat(pattern="dd/mm/yyyy")
    private Date startDate;
    @JsonFormat(pattern="dd/mm/yyyy")
    private Date endDate;
    @Min(0) @Max(1)
    private Double sale;
    private Boolean activeFlag;
    private String detail;
}
