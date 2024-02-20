package dev.chijiokeibekwe.jwtsecurity.service;

import dev.chijiokeibekwe.jwtsecurity.dto.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface JwtService {

    String generateAccessToken(CustomUserDetails userDetails);

    String generateRefreshToken(CustomUserDetails userDetails);

    Jws<Claims> validateToken(String token);
}
