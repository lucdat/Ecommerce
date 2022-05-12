package com.ecommerce.dto.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class OrderFilter {
    @NotNull
    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date dateStart;
    @NotNull
    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date dateEnd;
    private int page = 1;
    private int size = 10;
    private String status = "PENDING";
}
