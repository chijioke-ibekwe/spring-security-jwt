package com.codemuse.jwtsecurity.service.impl;

import com.codemuse.jwtsecurity.dto.response.UserResponse;
import com.codemuse.jwtsecurity.dto.request.UserRegistrationRequest;
import com.codemuse.jwtsecurity.entity.Role;
import com.codemuse.jwtsecurity.entity.User;
import com.codemuse.jwtsecurity.mapper.Mapper;
import com.codemuse.jwtsecurity.service.UserService;
import com.codemuse.jwtsecurity.repository.UserRepository;
import com.codemuse.jwtsecurity.service.RoleService;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse registerUser(UserRegistrationRequest userRegistrationRequest){

        if(userRepository.existsByUsername(userRegistrationRequest.getEmail()))
            throw new EntityExistsException(String.format("Email %s already exists", userRegistrationRequest.getEmail()));

        Role role = roleService.getRoleByUserType(userRegistrationRequest.getType());

        User user = User.builder()
                .firstName(userRegistrationRequest.getFirstName())
                .lastName(userRegistrationRequest.getLastName())
                .username(userRegistrationRequest.getEmail())
                .password(passwordEncoder.encode(userRegistrationRequest.getPassword()))
                .phoneNumber(userRegistrationRequest.getPhoneNumber())
                .roles(Collections.singleton(role))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        return Mapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable){

        return userRepository.findAll(pageable).map(Mapper::toUserResponse);
    }
}
