package com.ecommerce.service.impl;

import com.ecommerce.domain.Orders;
import com.ecommerce.domain.Role;
import com.ecommerce.domain.User;
import com.ecommerce.dto.converters.OrderConverter;
import com.ecommerce.dto.converters.UserConverter;
import com.ecommerce.dto.domain.*;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UniqueConstrainException;
import com.ecommerce.repositories.OrderRepo;
import com.ecommerce.repositories.RoleRepo;
import com.ecommerce.repositories.UserRepo;
import com.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final OrderRepo orderRepo;
    private final PasswordEncoder passwordEncoder;
    @Override
    public PageUserDTO findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        Page<User> list = userRepo.findAll(pageable);
        return UserConverter.covertToPageUserDTO(list);
    }

    @Override
    public UserDTO save(RegisterDTO register) {
            User user = UserConverter.covertRegisterDTOToUser(register);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User emailExist = userRepo.findByEmail(register.getEmail());
            User phoneExist = userRepo.findByPhone(register.getPhone());
            User usernameExist = userRepo.findByUsername(register.getUsername());

            if(phoneExist!=null) throw new UniqueConstrainException("phone:Phone already exists!");
            if(usernameExist!=null) throw new UniqueConstrainException("username:Username already exists!");
            if(emailExist!=null) throw new UniqueConstrainException("email:Email already exists!");

            User saveUser = userRepo.save(user);
            Role role = roleRepo.findByName("ROLE_USER");
            saveUser.getRoles().add(role);
            return UserConverter.covertToDTO(saveUser);
    }


    @Override
    public Map<String,String> addRoleToUser(long userId, long roleId) {
        Map<String,String> response = new HashMap<>();
        Role role = roleRepo.findById(roleId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Role ID %s not found",roleId)));
        User user = userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Role ID %s not found",userId)));
        user.getRoles().add(role);
        role.getUsers().add(user);
        response.put("message","success");
        return response;
    }

    @Override
    public Map<String,String> removeRoleInUser(long userId, long roleId) {
        Map<String,String> response = new HashMap<>();
        Role role = roleRepo.findById(roleId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Role ID %s not found",roleId)));
        User user = userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Role ID %s not found",userId)));
        user.getRoles().remove(role);
        role.getUsers().remove(user);
        response.put("message","success");
        return response;
    }

    @Override
    public PageOrderDTO getListOrders(Long id,int page, int size) {
        Pageable pageable =PageRequest.of(page-1,size);
        Page<Orders> orders = orderRepo.getOrdersByUserId(id,pageable);
        return OrderConverter.convertToPageDTO(orders);
    }

    @Override
    public Map<String,String> update(UserDTO userDTO) {
        Map<String,String> response = new HashMap<>();
        User user = userRepo.findById(userDTO.getId()).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User ID %s not found",userDTO.getId())));
        if(!userDTO.getPhone().equals(user.getPhone())){
            User phoneExist = userRepo.findByPhone(userDTO.getPhone());
            if(phoneExist!=null) throw new UniqueConstrainException("phone:Phone already exists!");
            user.setPhone(userDTO.getPhone());
        }
        if(!userDTO.getEmail().equals(user.getEmail())){
            User emailExist = userRepo.findByEmail(userDTO.getEmail());
            if(emailExist!=null) throw new UniqueConstrainException("email:email already exists!");
            user.setEmail(userDTO.getEmail());
        }
        user.setName(userDTO.getName());
        userRepo.save(user);
        response.put("message","success");
        return response;
    }
    @Override
    public UserDTO findById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User ID %s not found", id)));
        return UserConverter.covertToDTO(user);
    }

    @Override
    public UserDTO findByPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal!=null){
            String username;
            if(principal instanceof UserDetails){
                username = ((UserDetails) principal).getUsername();
            }else{
                username = principal.toString();
            }
            User user = userRepo.findByUsername(username);
            if(user!=null){
                return UserConverter.covertToDTO(user);
            }throw new ResourceNotFoundException(String.format("%s not found",username));
        }else throw new ResourceNotFoundException("User not found");
    }
}
