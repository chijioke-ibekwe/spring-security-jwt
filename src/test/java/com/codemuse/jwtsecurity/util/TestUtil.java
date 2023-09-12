package com.codemuse.jwtsecurity.util;

import com.codemuse.jwtsecurity.dto.request.UserRegistrationRequest;
import com.codemuse.jwtsecurity.dto.response.AuthenticationResponse;
import com.codemuse.jwtsecurity.dto.response.RoleResponse;
import com.codemuse.jwtsecurity.dto.response.UserResponse;
import com.codemuse.jwtsecurity.enums.UserType;

import java.util.List;
import java.util.Set;

public class TestUtil {

    public AuthenticationResponse getAuthenticationResponse() {

        return AuthenticationResponse.builder()
                .accessToken("e29Y8DYmn0M7j7J89/TQQXMpMQ0GHBaQUDQfJYvNOLLmXEhF9AByXUhY0gzZmTHa")
                .refreshToken("sMUpGdeBv3De6m/WptP4iniOGmASD2qnFbW+aAosDUFq1yCEdCfc0z7EPQIDAQAB")
                .tokenType("bearer")
                .expiresIn(1800)
                .build();
    }

    public UserRegistrationRequest getUserRegistrationRequest() {

        return UserRegistrationRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@starter.com")
                .password("password")
                .phoneNumber("+2348012345678")
                .type(UserType.ADMIN)
                .build();
    }

    public UserResponse getUserResponse() {

        return UserResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("john.doe@starter.com")
                .phoneNumber("+2348012345678")
                .roles(List.of(RoleResponse.builder()
                        .id(2L)
                        .name("ROLE_ADMIN")
                        .permissions(List.of("users:read"))
                        .build()))
                .build();
    }
}
