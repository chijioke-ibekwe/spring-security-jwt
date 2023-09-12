package com.codemuse.jwtsecurity.service.impl;

import com.codemuse.jwtsecurity.enums.RoleName;
import com.codemuse.jwtsecurity.entity.Role;
import com.codemuse.jwtsecurity.enums.UserType;
import com.codemuse.jwtsecurity.repository.RoleRepository;
import com.codemuse.jwtsecurity.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRoleByUserType(UserType userType) {
        RoleName roleName = switch (userType) {
            case CUSTOMER -> RoleName.ROLE_CUSTOMER;
            case ADMIN -> RoleName.ROLE_ADMIN;
        };

        return roleRepository.findByName(roleName.toString()).orElseThrow(() -> new
                EntityNotFoundException("Role not found"));
    }
}
