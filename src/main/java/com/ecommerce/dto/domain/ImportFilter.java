package com.ecommerce.dto.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ImportFilter {
    @NotNull
    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date date;
    private int type;
}
