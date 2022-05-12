package com.ecommerce.dto.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ForgetPassword {
    @NotBlank(message = "The username must be not blank")
    @Size(  min = 2
            ,max=45
            ,message = "The username must be between {min} and {max} characters")
    private String username;
    @NotBlank(message = "The email must be not blank")
    @Size(  min = 2
            ,max=100
            ,message = "The email must be between {min} and {max} characters")
    private String email;
}
