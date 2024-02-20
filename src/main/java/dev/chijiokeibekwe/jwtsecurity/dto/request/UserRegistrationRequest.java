package dev.chijiokeibekwe.jwtsecurity.dto.request;

import dev.chijiokeibekwe.jwtsecurity.enums.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {

    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Password is required")
    private String password;

    @NotNull(message = "Phone number is required")
    private String phoneNumber;

    @NotNull(message = "User type is required")
    @Enumerated(EnumType.STRING)
    private UserType type;
}
