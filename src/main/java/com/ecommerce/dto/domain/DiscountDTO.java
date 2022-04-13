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
    @NotBlank(message = "The start date must be not blank")
    @FutureOrPresent(message = "The start date must be future or present")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    @NotBlank(message = "The end date must be not blank")
    @Future(message = "The end date must be future ")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;
    @Min(0)
    @Max(1)
    private Double sale;
    private Boolean activeFlag;
    private String detail;
}
