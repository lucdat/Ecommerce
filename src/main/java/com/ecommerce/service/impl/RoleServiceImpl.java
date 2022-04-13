package com.ecommerce.service.impl;

import com.ecommerce.domain.Role;
import com.ecommerce.repositories.RoleRepo;
import com.ecommerce.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;
    @Override
    public Role save(Role role) {
        return roleRepo.save(role);
    }
}
