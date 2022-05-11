package com.ecommerce.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private long userId;
    private String username;
    @NotBlank(message = "The message must be not blank")
    @Size(  min = 1
            ,max=200
            ,message = "The message must be between {min} and {max} characters")
    private String message;
    private Date date;
}
