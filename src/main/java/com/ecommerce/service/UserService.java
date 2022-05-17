package com.ecommerce.service;

import com.ecommerce.dto.domain.*;

import java.util.Map;

public interface UserService {
    PageUserDTO findAll(int page, int size);
    UserDTO save(RegisterDTO register);
    Map<String,String> addRoleToUser(long userId,long roleId);
    Map<String,String> removeRoleInUser(long userId,long roleId);
    PageOrderDTO getListOrders(Long id,int page, int size);
    Map<String,String> update(UserDTO userDTO);
    UserDTO findById(Long id);

    UserDTO findByPrincipal();
}
