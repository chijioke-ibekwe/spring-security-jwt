package com.codemuse.jwtsecurity.repository;

import com.codemuse.jwtsecurity.entity.Role;
import com.codemuse.jwtsecurity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
