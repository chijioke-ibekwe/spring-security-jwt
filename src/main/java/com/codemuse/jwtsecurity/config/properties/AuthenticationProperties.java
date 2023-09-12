package com.codemuse.jwtsecurity.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@ConfigurationProperties(prefix = "authentication")
@Validated
public class AuthenticationProperties {

    private String secretKey;

    private TokenDetails accessToken;

    private TokenDetails refreshToken;

    @Data
    public static class TokenDetails {

        private Integer expirationInSeconds;
    }
}
