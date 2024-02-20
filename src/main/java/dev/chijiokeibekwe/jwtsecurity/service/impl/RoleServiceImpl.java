package dev.chijiokeibekwe.jwtsecurity.service.impl;

import dev.chijiokeibekwe.jwtsecurity.enums.RoleName;
import dev.chijiokeibekwe.jwtsecurity.entity.Role;
import dev.chijiokeibekwe.jwtsecurity.enums.UserType;
import dev.chijiokeibekwe.jwtsecurity.repository.RoleRepository;
import dev.chijiokeibekwe.jwtsecurity.service.RoleService;
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

        return roleRepository.findByName(roleName).orElseThrow(() -> new
                EntityNotFoundException("Role not found"));
    }
}
