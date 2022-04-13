package com.ecommerce.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private long id;
    @NotBlank(message = "The name must be not blank")
    @Size(  min = 1
            ,max=50
            ,message = "The name must be between {min} and {max} characters")
    private String name;
    @NotBlank(message = "The username must be not blank")
    @Size(  min = 1
            ,max=50
            ,message = "The username must be between {min} and {max} characters")
    private String username;
    @NotBlank(message = "The password must be not blank")
    @Size(  min = 1
            ,max=20
            ,message = "The password must be between {min} and {max} characters")
    private String password;
    @NotBlank(message = "The confirm password must be not blank")
    @Size(  min = 1
            ,max=20
            ,message = "The confirm password must be between {min} and {max} characters")
    private String confirmPassword;
    @NotBlank(message = "The phone must be not blank")
    @Size(  min = 10
            ,max=15
            ,message = "The phone must be between {min} and {max} characters")
    private String phone;
    @Size(  min = 10
            ,max=100
            ,message = "The must be between {min} and {max} characters")
    private String email;
    private Collection<String> roles = new ArrayList<>();
}
