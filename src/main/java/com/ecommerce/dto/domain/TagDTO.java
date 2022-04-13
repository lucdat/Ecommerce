package com.ecommerce.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private long id;
    @NotBlank(message = "The tag name must be not blank")
    @Size(  min = 2
            ,max=45
            ,message = "The tag name must be between {min} and {max} characters")
    private String name;
}
