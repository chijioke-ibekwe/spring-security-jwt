package dev.chijiokeibekwe.jwtsecurity.service;

import dev.chijiokeibekwe.jwtsecurity.entity.Role;
import dev.chijiokeibekwe.jwtsecurity.enums.RoleName;

public interface RoleService {

    Role getRoleByName(RoleName roleName);
}
