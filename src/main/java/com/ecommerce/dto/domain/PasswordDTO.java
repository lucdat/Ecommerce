package com.ecommerce.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDTO {
    @NotBlank(message = "The username must be not blank")
    @Size(  min = 1
            ,max=50
            ,message = "The username must be between {min} and {max} characters")
    private String username;
    @NotBlank(message = "The password must be not blank")
    @Size(  min = 1
            ,max=20
            ,message = "The password  must be between {min} and {max} characters")
    private String passwordOld;
    @NotBlank(message = "The password must be not blank")
    @Size(  min = 1
            ,max=20
            ,message = "The password  must be between {min} and {max} characters")
    private String newPassword;
    @NotBlank(message = "The confirm password must be not blank")
    @Size(  min = 1
            ,max=20
            ,message = "The confirm password must be between {min} and {max} characters")
    private String confirmPassword;
}
