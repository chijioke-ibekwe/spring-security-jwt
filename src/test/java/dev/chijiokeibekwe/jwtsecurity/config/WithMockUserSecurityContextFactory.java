package dev.chijiokeibekwe.jwtsecurity.config;

import dev.chijiokeibekwe.jwtsecurity.annotation.WithMockAdmin;
import dev.chijiokeibekwe.jwtsecurity.dto.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockAdmin> {

    @Override
    public SecurityContext createSecurityContext(WithMockAdmin user)
    {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        List<GrantedAuthority> grantedAuthorities = Stream.of(
                 "users:read"
                )
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new CustomUserDetails(1L, "John", "Doe", user.phone(), grantedAuthorities, null, user.email(), true, true, true, true, true),
                null,
                grantedAuthorities
        );

        securityContext.setAuthentication(authentication);

        return securityContext;
    }
}
