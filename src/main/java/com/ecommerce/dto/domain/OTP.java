package com.ecommerce.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
public class OTP {
    private Date date;
    @NotNull
    private Integer otp;
    @NotBlank(message = "The username must be not blank")
    @Size(  min = 2
            ,max=45
            ,message = "The username must be between {min} and {max} characters")
    private String username;
}
