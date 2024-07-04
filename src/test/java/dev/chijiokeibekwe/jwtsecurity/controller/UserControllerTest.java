package dev.chijiokeibekwe.jwtsecurity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.chijiokeibekwe.jwtsecurity.annotation.WithMockAdmin;
import dev.chijiokeibekwe.jwtsecurity.auth.CustomUserDetailsService;
import dev.chijiokeibekwe.jwtsecurity.auth.DelegatedAuthenticationEntryPoint;
import dev.chijiokeibekwe.jwtsecurity.config.SecurityConfig;
import dev.chijiokeibekwe.jwtsecurity.config.properties.RsaKeyProperties;
import dev.chijiokeibekwe.jwtsecurity.dto.request.UserRegistrationRequest;
import dev.chijiokeibekwe.jwtsecurity.dto.response.UserResponse;
import dev.chijiokeibekwe.jwtsecurity.enums.ResponseStatus;
import dev.chijiokeibekwe.jwtsecurity.service.UserService;
import dev.chijiokeibekwe.jwtsecurity.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@EnableConfigurationProperties(value = {RsaKeyProperties.class})
@WebMvcTest(value = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @MockBean
    private DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint;

    private final TestUtil testUtil = new TestUtil();

    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Test
    void testUserRegistration() throws Exception {
        UserResponse response = testUtil.getUserResponse();
        UserRegistrationRequest request = testUtil.getUserRegistrationRequest();

        when(userService.registerUser(request)).thenReturn(response);

        this.mockMvc.perform(post("/api/v1/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESSFUL.getValue()))
                .andExpect(jsonPath("$.message").value("User registered successfully"))
                .andExpect(jsonPath("$.data.id").value(response.getId()))
                .andExpect(jsonPath("$.data.firstName").value(response.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(response.getLastName()))
                .andExpect(jsonPath("$.data.username").value(response.getUsername()))
                .andExpect(jsonPath("$.data.phoneNumber").value(response.getPhoneNumber()))
                .andExpect(jsonPath("$.data.roles[0].name").value(response.getRoles().get(0).getName().getValue()));
    }

    @Test
    void testUserRegistration_whenRequestHasEmailAsNull() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest(
                "John",
                "Doe",
                null,
                "password",
                "+2348012345678"
        );

        this.mockMvc.perform(post("/api/v1/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(ResponseStatus.FAILED.getValue()))
                .andExpect(jsonPath("$.message").value("Email is required"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    @WithMockAdmin
    void testGetAllUsers() throws Exception {
        UserResponse response = testUtil.getUserResponse();

        when(userService.getAllUsers(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.singletonList(response)));

        this.mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESSFUL.getValue()))
                .andExpect(jsonPath("$.data.content[0].id").value(response.getId()))
                .andExpect(jsonPath("$.data.content[0].firstName").value(response.getFirstName()))
                .andExpect(jsonPath("$.data.content[0].lastName").value(response.getLastName()))
                .andExpect(jsonPath("$.data.content[0].username").value(response.getUsername()))
                .andExpect(jsonPath("$.data.content[0].phoneNumber").value(response.getPhoneNumber()))
                .andExpect(jsonPath("$.data.content[0].roles[0].name").value(response.getRoles().get(0).getName().getValue()));
    }

    @Test
    @WithMockAdmin
    void testGetUser() throws Exception {
        UserResponse response = testUtil.getUserResponse();

        when(userService.getUser(1L)).thenReturn(response);

        this.mockMvc.perform(get("/api/v1/users/{userId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESSFUL.getValue()))
                .andExpect(jsonPath("$.data.id").value(response.getId()))
                .andExpect(jsonPath("$.data.firstName").value(response.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(response.getLastName()))
                .andExpect(jsonPath("$.data.username").value(response.getUsername()))
                .andExpect(jsonPath("$.data.phoneNumber").value(response.getPhoneNumber()))
                .andExpect(jsonPath("$.data.roles[0].name").value(response.getRoles().get(0).getName().getValue()));
    }
}
