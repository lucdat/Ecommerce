package com.ecommerce.dto.domain;

import com.ecommerce.domain.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private long id;
    @JsonIgnore
    private long productId;
    @NotBlank(message = "The name must be not blank")
    @Size(  min = 2
            ,max=45
            ,message = "The name must be between {min} and {max} characters")
    private String name;
    @NotBlank(message = "The phone must be not blank")
    @Size(  min = 2
            ,max=20
            ,message = "The phone must be between {min} and {max} characters")
    private String phone;
    @Size(  min = 2
            ,max=100
            ,message = "The email  must be between {min} and {max} characters")
    private String email;
    @NotBlank(message = "The address must be not blank")
    @Size(  min = 2
            ,max=200
            ,message = "The address  must be between {min} and {max} characters")
    private String address;
    private String note;
    private OrderStatus status;
    private int amount;
    private double totalPrice;
}
