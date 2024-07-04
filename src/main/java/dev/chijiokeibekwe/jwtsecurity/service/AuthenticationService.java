package dev.chijiokeibekwe.jwtsecurity.service;

import dev.chijiokeibekwe.jwtsecurity.dto.request.AuthenticationRequest;
import dev.chijiokeibekwe.jwtsecurity.dto.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);
}
