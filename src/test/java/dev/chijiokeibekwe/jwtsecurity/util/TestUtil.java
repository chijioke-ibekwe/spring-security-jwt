package dev.chijiokeibekwe.jwtsecurity.util;

import dev.chijiokeibekwe.jwtsecurity.dto.request.AuthenticationRequest;
import dev.chijiokeibekwe.jwtsecurity.dto.request.UserRegistrationRequest;
import dev.chijiokeibekwe.jwtsecurity.dto.response.AuthenticationResponse;
import dev.chijiokeibekwe.jwtsecurity.dto.response.RoleResponse;
import dev.chijiokeibekwe.jwtsecurity.dto.response.UserResponse;
import dev.chijiokeibekwe.jwtsecurity.entity.Permission;
import dev.chijiokeibekwe.jwtsecurity.entity.Role;
import dev.chijiokeibekwe.jwtsecurity.entity.User;
import dev.chijiokeibekwe.jwtsecurity.enums.RoleName;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.*;

public class TestUtil {

    public AuthenticationResponse getAuthenticationResponse() {

        return new AuthenticationResponse(
                "e29Y8DYmn0M7j7J89/TQQXMpMQ0GHBaQUDQfJYvNOLLmXEhF9AByXUhY0gzZmTHa",
                "bearer",
                1800
        );
    }

    public AuthenticationRequest getAuthenticationRequest() {

        return new AuthenticationRequest(
                "john.doe@library.com",
                "password"
        );
    }

    public UserRegistrationRequest getUserRegistrationRequest() {

        return new UserRegistrationRequest(
                "John",
                "Doe",
                "john.doe@library.com",
                "password",
                "+2348012345678"
        );
    }

    public UserResponse getUserResponse() {

        return UserResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("john.doe@library.com")
                .phoneNumber("+2348012345678")
                .roles(Collections.singletonList(RoleResponse.builder()
                        .id(2L)
                        .name(RoleName.ROLE_USER)
                        .permissions(List.of("users:read"))
                        .build()))
                .build();
    }

    public Role getUserRole() {

        return Role.builder()
                .id(1L)
                .description("Role with user permissions")
                .name(RoleName.ROLE_USER)
                .permissions(this.getUserPermissions())
                .build();
    }

    private List<Permission> getUserPermissions() {

        return Collections.singletonList(
                Permission.builder()
                        .name("users:read")
                        .description("Ability to fetch all users")
                        .build()
        );
    }

    public User getUser() {

        return User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("john.doe@library.com")
                .phoneNumber("+2348012345678")
                .roles(Collections.singletonList(this.getUserRole()))
                .build();
    }

    public Jwt getJwt() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("algo", "HS256");

        Map<String, Object> claims = new HashMap<>();
        claims.put("issuedBy", "self");

        return new Jwt(
                this.getAuthenticationResponse().accessToken(),
                Instant.now(),
                Instant.now().plusSeconds(1800L),
                headers,
                claims
        );
    }
}
