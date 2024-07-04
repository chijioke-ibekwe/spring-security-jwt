package dev.chijiokeibekwe.jwtsecurity.controller;

import dev.chijiokeibekwe.jwtsecurity.common.ResponseObject;
import dev.chijiokeibekwe.jwtsecurity.dto.request.UserRegistrationRequest;
import dev.chijiokeibekwe.jwtsecurity.dto.response.UserResponse;
import dev.chijiokeibekwe.jwtsecurity.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static dev.chijiokeibekwe.jwtsecurity.enums.ResponseStatus.SUCCESSFUL;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @SecurityRequirements
    @Operation(summary = "Sign up or register", description = "Sign up as a user")
    @PostMapping
    public ResponseObject<UserResponse> registerUser(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest){

        return new ResponseObject<>(
                SUCCESSFUL,
                "User registered successfully",
                userService.registerUser(userRegistrationRequest)
        );
    }

    @Operation(summary = "Fetch all users", description = "Fetch all users on the platform")
    @PageableAsQueryParam
    @GetMapping
    @PreAuthorize("hasAuthority('users:read')")
    public ResponseObject<Page<UserResponse>> getAllUsers(@PageableDefault(
                                                            sort = "id", direction = Sort.Direction.DESC
                                                          ) @Parameter(hidden = true) Pageable pageable){

        return new ResponseObject<>(
                SUCCESSFUL,
                null,
                userService.getAllUsers(pageable)
        );
    }

    @Operation(summary = "Fetch a single user", description = "Fetch a single user on the platform")
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('users:read')")
    public ResponseObject<UserResponse> getUser(@PathVariable Long userId){

        return new ResponseObject<>(
                SUCCESSFUL,
                null,
                userService.getUser(userId)
        );
    }
}
