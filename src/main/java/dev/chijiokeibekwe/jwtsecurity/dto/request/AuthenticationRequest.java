package dev.chijiokeibekwe.jwtsecurity.dto.request;

import jakarta.validation.constraints.NotNull;

public record AuthenticationRequest(
        @NotNull(message = "Username is required")
        String username,

        @NotNull(message = "Password is required")
        String password
)
{
    //
}
