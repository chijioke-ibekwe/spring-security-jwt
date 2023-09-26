package com.codemuse.jwtsecurity.service;

import com.codemuse.jwtsecurity.dto.response.UserResponse;
import com.codemuse.jwtsecurity.entity.User;
import com.codemuse.jwtsecurity.enums.RoleName;
import com.codemuse.jwtsecurity.repository.UserRepository;
import com.codemuse.jwtsecurity.service.impl.UserServiceImpl;
import com.codemuse.jwtsecurity.util.TestUtil;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserServiceImpl.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleService roleService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private final TestUtil testUtil = new TestUtil();

    @Test
    public void testRegisterUser() {
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(roleService.getRoleByUserType(any())).thenReturn(testUtil.getRole());
        when(passwordEncoder.encode(any())).thenReturn("abcde");
        when(userRepository.save(any(User.class))).thenReturn(testUtil.getUser());

        UserResponse response = userService.registerUser(testUtil.getUserRegistrationRequest());

        verify(userRepository, times(1)).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue().getFirstName()).isEqualTo("John");
        assertThat(userArgumentCaptor.getValue().getLastName()).isEqualTo("Doe");
        assertThat(userArgumentCaptor.getValue().getUsername()).isEqualTo("john.doe@starter.com");
        assertThat(userArgumentCaptor.getValue().getPassword()).isEqualTo("abcde");

        assertThat(response.getRoles().get(0).getId()).isEqualTo(1L);
        assertThat(response.getRoles().get(0).getName()).isEqualTo(RoleName.ROLE_ADMIN);
        assertThat(response.getRoles().get(0).getPermissions()).contains("user:read", "user:write");
    }

    @Test
    public void testRegisterUser_whenUsernameAlreadyExists() {
        when(userRepository.existsByUsername(any())).thenReturn(true);

        assertThatThrownBy(() -> userService.registerUser(testUtil.getUserRegistrationRequest()))
                .isInstanceOf(EntityExistsException.class)
                .hasMessage("Email john.doe@starter.com already exists");
    }
}
