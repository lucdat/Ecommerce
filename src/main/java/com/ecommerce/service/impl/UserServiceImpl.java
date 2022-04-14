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
import com.ecommerce.security.JwtUtils;
import com.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtUtils jwtUtils;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final OrderRepo orderRepo;
    private final PasswordEncoder passwordEncoder;
    @Override
    public PageUserDTO findAll(int page, int size) {
        Pageable padeable = PageRequest.of(page-1,size);
        Page<User> list = userRepo.findAll(padeable);
        return UserConverter.covertToPageUserDTO(list);
    }

    @Override
    public UserDTO save(RegisterDTO register) {
        if(register!=null){
            User user = UserConverter.covertRegisterDTOToUser(register);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User emailExist = userRepo.findByEmail(register.getEmail());
            User phoneExist = userRepo.findByEmail(register.getPhone());
            User usernameExist = userRepo.findByUsername(register.getUsername());
            if(emailExist!=null){
                throw new UniqueConstrainException("email:Email already exists!");
            }
            if(phoneExist!=null){
                throw new UniqueConstrainException("phone:Phone already exists!");
            }
            if(usernameExist!=null){
                throw new UniqueConstrainException("username:Username already exists!");
            }
            User saveUser = userRepo.save(user);
            Collection<Orders> orders = orderRepo.findByPhone(register.getPhone());
            for(Orders o : orders){
                saveUser.getOrders().add(o);
                o.setUser(saveUser);
            }
            if(register.getRoles()!=null){
                Collection<Role> roles = register.getRoles().stream().map(roleRepo::findByName).collect(Collectors.toList());
                saveUser.setRoles(roles);
            }
            return UserConverter.covertToDTO(saveUser);
        }
        return null;
    }


    @Override
    public String addRoleToUser(long userId, long roleId) {
        Role role = roleRepo.findById(roleId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Role ID %s not found",roleId)));
        User user = userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Role ID %s not found",userId)));
        user.getRoles().add(role);
        role.getUsers().add(user);
        return "success";
    }

    @Override
    public String removeRoleInUser(long userId, long roleId) {
        Role role = roleRepo.findById(roleId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Role ID %s not found",roleId)));
        User user = userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Role ID %s not found",userId)));
        user.getRoles().remove(role);
        role.getUsers().remove(user);
        return "success";
    }

    @Override
    public PageOrderDTO getListOrders(Long id,int page, int size) {
        Pageable pageable =PageRequest.of(page-1,size);
        Page<Orders> orders = orderRepo.getOrdersByUserId(id,pageable);
        return OrderConverter.convertToPageDTO(orders);
    }

    @Override
    public String update(UserDTO userDTO) {
        User user = userRepo.findById(userDTO.getId()).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User ID %s not found",userDTO.getId())));
        if(!userDTO.getPhone().equals(user.getPhone())){
            User phoneExist = userRepo.findByPhone(user.getPhone());
            if(phoneExist!=null){
                throw new UniqueConstrainException("phone:Phone already exists!");
            }
        }
        if(!userDTO.getEmail().equals(user.getEmail())){
            User emailExist = userRepo.findByEmail(user.getEmail());
            if(emailExist!=null){
                throw new UniqueConstrainException("email:email already exists!");
            }
        }
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        userRepo.save(user);
        return "success";
    }

    @Override
    public Map<String, String> changePassword(PasswordDTO passwordDTO) {
        Map<String,String> response = new HashMap<>();
        User user = userRepo.findByUsername(passwordDTO.getUsername());
        if(user==null) {
            throw new ResourceNotFoundException(String.format("%s not found",passwordDTO.getUsername()));
        }
        else{
            if(!passwordEncoder.matches(passwordDTO.getPasswordOld(), user.getPassword())){
                response.put("message","Password old not valid");
            }else{
                if(!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())){
                    response.put("message","Password not valid");
                }else{
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    user.getRoles().forEach(role-> authorities.add(new SimpleGrantedAuthority(role.getName())));
                    String access_token = jwtUtils.generateToken(new org.springframework.security.core.userdetails
                            .User(user.getUsername(),passwordEncoder.encode(passwordDTO.getNewPassword()), authorities));
                    user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
                    userRepo.save(user);
                    response.put("access_token",access_token);
                }
            }
        }
        return response;
    }
}
