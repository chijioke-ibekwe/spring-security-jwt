package dev.chijiokeibekwe.jwtsecurity.dto.request;

import jakarta.validation.constraints.NotNull;

public record UserRegistrationRequest(
        @NotNull(message = "First name is required")
        String firstName,

        @NotNull(message = "Last name is required")
        String lastName,

        @NotNull(message = "Email is required")
        String email,

        @NotNull(message = "Password is required")
        String password,

        @NotNull(message = "Phone number is required")
        String phoneNumber
)
{
    //
}
