package com.ecommerce.dto.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
    @NotBlank(message = "The username must be not blank")
    @Size(  min = 2
            ,max=45
            ,message = "The username must be between {min} and {max} characters")
    private String username;
    @NotBlank(message = "The password must be not blank")
    @Size(  min = 2
            ,max=200
            ,message = "The password must be between {min} and {max} characters")
    private String password;
}
