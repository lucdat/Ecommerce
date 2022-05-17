package com.ecommerce.dto.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class OrderFilter {
    @NotNull
    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date date;
    @NotNull
    private String status = "PENDING";
    private int type;
}
