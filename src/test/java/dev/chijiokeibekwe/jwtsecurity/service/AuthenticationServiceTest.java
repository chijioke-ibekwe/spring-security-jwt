package dev.chijiokeibekwe.jwtsecurity.service;

import dev.chijiokeibekwe.jwtsecurity.annotation.WithMockAdmin;
import dev.chijiokeibekwe.jwtsecurity.config.properties.RsaKeyProperties;
import dev.chijiokeibekwe.jwtsecurity.dto.request.AuthenticationRequest;
import dev.chijiokeibekwe.jwtsecurity.dto.response.AuthenticationResponse;
import dev.chijiokeibekwe.jwtsecurity.service.impl.AuthenticationServiceImpl;
import dev.chijiokeibekwe.jwtsecurity.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AuthenticationServiceImpl.class})
public class AuthenticationServiceTest {
    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private RsaKeyProperties rsaKeyProperties;

    private final TestUtil testUtil = new TestUtil();

    @Test
    @WithMockAdmin
    public void testLogin() {
        AuthenticationRequest request = testUtil.getAuthenticationRequest();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        when(authenticationManager.authenticate(token)).thenReturn(authentication);
        when(jwtService.generateAccessToken(authentication)).thenReturn(testUtil.getAuthenticationResponse().accessToken());
        when(rsaKeyProperties.expirationInSeconds()).thenReturn(1800);

        AuthenticationResponse response = authenticationService.login(request);

        assertThat(response).isEqualTo(testUtil.getAuthenticationResponse());
    }
}