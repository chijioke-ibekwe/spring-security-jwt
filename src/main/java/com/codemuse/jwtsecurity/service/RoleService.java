package com.codemuse.jwtsecurity.service;

import com.codemuse.jwtsecurity.entity.Role;
import com.codemuse.jwtsecurity.enums.UserType;

public interface RoleService {

    Role getRoleByUserType(UserType userType);
}
