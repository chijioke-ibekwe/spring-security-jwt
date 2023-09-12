package com.codemuse.jwtsecurity.service;

import com.codemuse.jwtsecurity.dto.response.UserResponse;
import com.codemuse.jwtsecurity.dto.request.UserRegistrationRequest;
import com.codemuse.jwtsecurity.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse registerUser(UserRegistrationRequest userRegistrationRequest);

    Page<UserResponse> getAllUsers(Pageable pageable);
}
