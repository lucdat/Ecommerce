package com.ecommerce.dto.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ImportDTO {
    private long id;
    private Date date;
    private int amount;
    private Double totalPrice;
    private String name;
    private String description;
    private UserDTO user;
}
