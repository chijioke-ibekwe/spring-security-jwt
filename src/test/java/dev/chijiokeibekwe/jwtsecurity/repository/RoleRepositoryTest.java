package dev.chijiokeibekwe.jwtsecurity.repository;

import dev.chijiokeibekwe.jwtsecurity.entity.Role;
import dev.chijiokeibekwe.jwtsecurity.enums.RoleName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testFindByName() {

        Optional<Role> result = roleRepository.findByName(RoleName.ROLE_ADMIN);

        assertThat(result.get().getName()).isEqualTo(RoleName.ROLE_ADMIN);
    }
}
