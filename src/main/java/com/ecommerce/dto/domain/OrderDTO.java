package com.ecommerce.dto.domain;

import com.ecommerce.domain.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private long id;
    @JsonIgnore
    private long productId;
    private String name;
    private String phone;
    private String email;
    @NotBlank(message = "The address must be not blank")
    @Size(  min = 2
            ,max=200
            ,message = "The address  must be between {min} and {max} characters")
    private String address;
    private Date date;
    private String note;
    private OrderStatus status;
    private int amount;
    private double totalPrice;
}
