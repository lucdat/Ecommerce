package com.ecommerce.repositories;

import com.ecommerce.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByPhone(String phone);
}
