package com.ecommerce.dto.converters;

import com.ecommerce.domain.Role;
import com.ecommerce.domain.User;
import com.ecommerce.dto.domain.*;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserConverter {
    public static UserDTO covertToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setPhone(user.getPhone());
        userDTO.setName(user.getName());
        userDTO.setUsername(user.getUsername());
        userDTO.setActiveFlag(user.getActiveFlag());
        userDTO.setEmail(user.getEmail());
        Collection<RoleDTO> roles = convertToListRoleDTO(user.getRoles());
        userDTO.setRoles(roles);
        return userDTO;
    }
    public static Collection<UserDTO> convertToListUserDTO(Collection<User> users){
        return users.stream().map(UserConverter::covertToDTO).collect(Collectors.toList());
    }
    public static RoleDTO covertRoleToRoleDTO(Role role){
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }
    public static Collection<RoleDTO> convertToListRoleDTO(Collection<Role> roles){
        return roles.stream().map(UserConverter::covertRoleToRoleDTO).collect(Collectors.toList());
    }
    public static PageUserDTO covertToPageUserDTO(Page<User> list){
        Collection<UserDTO> users = convertToListUserDTO(list.toList());
        PageDTO page = new PageDTO(list.getTotalPages(), list.getSize(), list.isFirst(), list.isLast());
        return new PageUserDTO(users,page);
    }
    public static User covertRegisterDTOToUser(RegisterDTO register){
        User user = new User();
        user.setName(register.getName());
        user.setPhone(register.getPhone());
        user.setUsername(register.getUsername());
        user.setPassword(register.getPassword());
        user.setEmail(register.getEmail());
        return user;
    }
}
