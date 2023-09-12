package com.codemuse.jwtsecurity.service;

import com.codemuse.jwtsecurity.dto.response.AuthenticationResponse;
import com.codemuse.jwtsecurity.dto.request.AuthenticationRequest;
import com.codemuse.jwtsecurity.dto.request.RefreshTokenRequest;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);

    AuthenticationResponse refreshToken(RefreshTokenRequest request);

    void logout(String username);
}
