package dev.chijiokeibekwe.jwtsecurity.dto.response;

public record AuthenticationResponse(
        String accessToken,

        String tokenType,

        Integer expiresIn
)
{
    //
}
