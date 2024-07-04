package dev.chijiokeibekwe.jwtsecurity.service;

import dev.chijiokeibekwe.jwtsecurity.dto.request.UserRegistrationRequest;
import dev.chijiokeibekwe.jwtsecurity.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse registerUser(UserRegistrationRequest userRegistrationRequest);

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse getUser(Long userId);
}
