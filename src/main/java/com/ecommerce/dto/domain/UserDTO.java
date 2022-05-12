package com.ecommerce.dto.domain;

import lombok.*;

import java.util.Collection;
import java.util.HashSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private long id;
    private String phone;
    private String name;
    private String username;
    private String email;
    private Boolean activeFlag;
    private Collection<RoleDTO> roles = new HashSet<>();

}
