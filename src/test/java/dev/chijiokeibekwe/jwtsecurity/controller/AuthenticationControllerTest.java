package dev.chijiokeibekwe.jwtsecurity.controller;

import dev.chijiokeibekwe.jwtsecurity.annotation.WithMockAdmin;
import dev.chijiokeibekwe.jwtsecurity.auth.CustomUserDetailsService;
import dev.chijiokeibekwe.jwtsecurity.common.ResponseObject;
import dev.chijiokeibekwe.jwtsecurity.config.SecurityConfig;
import dev.chijiokeibekwe.jwtsecurity.dto.request.AuthenticationRequest;
import dev.chijiokeibekwe.jwtsecurity.dto.request.RefreshTokenRequest;
import dev.chijiokeibekwe.jwtsecurity.dto.response.AuthenticationResponse;
import dev.chijiokeibekwe.jwtsecurity.service.AuthenticationService;
import dev.chijiokeibekwe.jwtsecurity.service.JwtService;
import dev.chijiokeibekwe.jwtsecurity.util.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    private final TestUtil testUtil = new TestUtil();

    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Test
    void testLogin() throws Exception {
        AuthenticationResponse response = testUtil.getAuthenticationResponse();
        AuthenticationRequest request = AuthenticationRequest.builder()
                .username("john.doe@starter.com")
                .password("password")
                .build();

        when(authenticationService.login(any(AuthenticationRequest.class))).thenReturn(response);

        this.mockMvc.perform(post("/api/v1/auth/login")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(response.getAccessToken()))
                .andExpect(jsonPath("$.refreshToken").value(response.getRefreshToken()))
                .andExpect(jsonPath("$.tokenType").value(response.getTokenType()))
                .andExpect(jsonPath("$.expiresIn").value(response.getExpiresIn()));
    }

    @Test
    void testRefreshToken() throws Exception {
        AuthenticationResponse response = testUtil.getAuthenticationResponse();
        RefreshTokenRequest request = RefreshTokenRequest.builder()
                .refreshToken("sMUpGdeBv3De6m/WptP4iniOGmASD2qnFbW+aAosDUFq1yCEdCfc0z7EPQIDAQAB")
                .build();

        when(authenticationService.refreshToken(any(RefreshTokenRequest.class))).thenReturn(response);

        this.mockMvc.perform(post("/api/v1/auth/refresh")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(response.getAccessToken()))
                .andExpect(jsonPath("$.refreshToken").value(response.getRefreshToken()))
                .andExpect(jsonPath("$.tokenType").value(response.getTokenType()))
                .andExpect(jsonPath("$.expiresIn").value(response.getExpiresIn()));
    }

    @Test
    @WithMockAdmin
    void testLogout() throws Exception {

        this.mockMvc.perform(post("/api/v1/auth/logout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseObject.ResponseStatus.SUCCESSFUL.getValue()))
                .andExpect(jsonPath("$.message").value("User logged out successfully"));
    }
}