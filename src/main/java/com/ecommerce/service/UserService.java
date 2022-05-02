package com.ecommerce.service;

import com.ecommerce.dto.domain.*;

import java.util.Map;

public interface UserService {
    PageUserDTO findAll(int page, int size);
    UserDTO save(RegisterDTO register);
    String addRoleToUser(long userId,long roleId);
    String removeRoleInUser(long userId,long roleId);
    PageOrderDTO getListOrders(Long id,int page, int size);
    String update(UserDTO userDTO);
    Map<String,String> changePassword(PasswordDTO passwordDTO);
    UserDTO findById(Long id);

    UserDTO findByUsername(String username);
}
