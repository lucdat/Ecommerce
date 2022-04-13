package com.ecommerce.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageUserDTO {
    private Collection<UserDTO> users = new HashSet<>();
    private PageDTO page;
}
