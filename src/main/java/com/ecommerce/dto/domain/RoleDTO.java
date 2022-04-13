package com.ecommerce.dto.domain;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private long id;
    @Size(  min = 3
            ,max=45
            ,message = "The role name must be between {min} and {max} characters")
    private String name;
}
