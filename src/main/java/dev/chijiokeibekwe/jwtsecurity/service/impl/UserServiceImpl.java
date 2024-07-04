package dev.chijiokeibekwe.jwtsecurity.service.impl;

import dev.chijiokeibekwe.jwtsecurity.dto.request.UserRegistrationRequest;
import dev.chijiokeibekwe.jwtsecurity.dto.response.UserResponse;
import dev.chijiokeibekwe.jwtsecurity.entity.Role;
import dev.chijiokeibekwe.jwtsecurity.entity.User;
import dev.chijiokeibekwe.jwtsecurity.enums.RoleName;
import dev.chijiokeibekwe.jwtsecurity.mapper.Mapper;
import dev.chijiokeibekwe.jwtsecurity.service.UserService;
import dev.chijiokeibekwe.jwtsecurity.repository.UserRepository;
import dev.chijiokeibekwe.jwtsecurity.service.RoleService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse registerUser(UserRegistrationRequest userRegistrationRequest){

        if(userRepository.existsByUsername(userRegistrationRequest.email()))
            throw new EntityExistsException(String.format("Email %s already exists", userRegistrationRequest.email()));

        Role role = roleService.getRoleByName(RoleName.ROLE_USER);

        User user = User.builder()
                .firstName(userRegistrationRequest.firstName())
                .lastName(userRegistrationRequest.lastName())
                .username(userRegistrationRequest.email())
                .password(passwordEncoder.encode(userRegistrationRequest.password()))
                .phoneNumber(userRegistrationRequest.phoneNumber())
                .roles(Collections.singletonList(role))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        User savedUser = userRepository.save(user);

        //TODO: send verification email to savedUser

        return Mapper.toUserResponse(savedUser);
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable){

        return userRepository.findAll(pageable).map(Mapper::toUserResponse);
    }

    @Override
    public UserResponse getUser(Long userId) {

        return userRepository.findById(userId).map(Mapper::toUserResponse)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
