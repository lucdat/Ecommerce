package com.ecommerce;


import com.ecommerce.domain.Role;
import com.ecommerce.dto.domain.RegisterDTO;
import com.ecommerce.dto.domain.UserDTO;
import com.ecommerce.service.RoleService;
import com.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.IntStream;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
//	@Bean
//	CommandLineRunner run(UserService userService, RoleService roleService){
//		return args -> {
//			Role role1 = new Role();
//			role1.setName("ROLE_USER");
//			Role role2 = new Role();
//			role2.setName("ROLE_SALE");
//			Role role3 = new Role();
//			role3.setName("ROLE_ADMIN");
//			Role newRole1 = roleService.save(role1);
//			Role newRole2 = roleService.save(role2);
//			Role newRole3 = roleService.save(role3);
//			IntStream.rangeClosed(1,3).forEach(i->{
//				RegisterDTO user = new RegisterDTO();
//				user.setName("user"+i);
//				user.setUsername("user"+i);
//				user.setPassword("123");
//				user.setConfirmPassword("123");
//				user.setEmail("ussizeer"+i+"@gmail.com");
//				user.setPhone("012345678"+i);
//				UserDTO newUser = userService.save(user);
//				if(i==1) userService.addRoleToUser(newUser.getId(),newRole1.getId());
//				if(i==2) userService.addRoleToUser(newUser.getId(),newRole2.getId());
//				if(i==3) userService.addRoleToUser(newUser.getId(),newRole3.getId());
//			});
//		};
//	}
}
