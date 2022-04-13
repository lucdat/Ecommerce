package com.ecommerce.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {
    private long id;
    @NotBlank(message = "The name must be not blank")
    @Size(  min = 2
            ,max=45
            ,message = "The name must be between {min} and {max} characters")
    private String name;
    private String logo;
    private Date createAt;
}
