package com.codemuse.jwtsecurity.service.impl;

import com.codemuse.jwtsecurity.dto.response.AuthenticationResponse;
import com.codemuse.jwtsecurity.config.properties.AuthenticationProperties;
import com.codemuse.jwtsecurity.dto.request.AuthenticationRequest;
import com.codemuse.jwtsecurity.dto.CustomUserDetails;
import com.codemuse.jwtsecurity.dto.request.RefreshTokenRequest;
import com.codemuse.jwtsecurity.entity.Token;
import com.codemuse.jwtsecurity.entity.User;
import com.codemuse.jwtsecurity.enums.TokenType;
import com.codemuse.jwtsecurity.mapper.Mapper;
import com.codemuse.jwtsecurity.repository.TokenRepository;
import com.codemuse.jwtsecurity.repository.UserRepository;
import com.codemuse.jwtsecurity.service.AuthenticationService;
import com.codemuse.jwtsecurity.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private final UserRepository userRepository;

    private final AuthenticationProperties authenticationProperties;

    @Override
    @Transactional
    public AuthenticationResponse login(AuthenticationRequest request) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        User user = userRepository.findByUsername(authentication.getName()).get();
        CustomUserDetails userDetails = Mapper.toCustomUserDetails(user);

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        this.revokeUserToken(user, Arrays.asList(TokenType.ACCESS_TOKEN, TokenType.REFRESH_TOKEN));
        this.saveUserToken(user, accessToken, TokenType.ACCESS_TOKEN);
        this.saveUserToken(user, refreshToken, TokenType.REFRESH_TOKEN);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("bearer")
                .expiresIn(authenticationProperties.getAccessToken().getExpirationInSeconds())
                .build();
    }

    @Override
    @Transactional
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        Jws<Claims> claimsJws = jwtService.validateToken(refreshToken);

        Claims body = claimsJws.getBody();
        User user = userRepository.findByUsername((String) body.get("username")).get();
        CustomUserDetails userDetails = Mapper.toCustomUserDetails(user);

        String accessToken = jwtService.generateAccessToken(userDetails);

        this.revokeUserToken(user, List.of(TokenType.ACCESS_TOKEN));
        this.saveUserToken(user, accessToken, TokenType.ACCESS_TOKEN);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("bearer")
                .expiresIn(authenticationProperties.getAccessToken().getExpirationInSeconds())
                .build();
    }

    @Override
    @Transactional
    public void logout(String username) {
        User user = userRepository.findByUsername(username).get();

        revokeUserToken(user, Arrays.asList(TokenType.ACCESS_TOKEN, TokenType.REFRESH_TOKEN));
    }

    @Transactional
    public void revokeUserToken(User user, List<TokenType> tokenTypes) {

        tokenTypes.forEach(tt -> {
            Optional<Token> token = tokenRepository.findByUserIdAndTokenType(user.getId(), tt);

            token.ifPresent(t -> {
                t.setRevoked(true);
                tokenRepository.save(t);
            });
        });
    }

    private void saveUserToken(User user, String token, TokenType tokenType) {

        Token t = Token.builder()
                .token(token)
                .tokenType(tokenType)
                .user(user)
                .revoked(false)
                .build();

        tokenRepository.save(t);
    }
}
