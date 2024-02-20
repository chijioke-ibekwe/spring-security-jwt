package dev.chijiokeibekwe.jwtsecurity.auth;

import dev.chijiokeibekwe.jwtsecurity.service.JwtService;
import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Slf4j
@Component
@AllArgsConstructor
public class JwtTokenVerifierFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace("Bearer ", "");

        try{
            Jws<Claims> claimsJws = jwtService.validateToken(token);
            Claims body = claimsJws.getBody();

            String username = (String) body.get("username");

            String authorities;
            if(body.get("authorities") == null) {
                throw new MissingClaimException(claimsJws.getHeader(), body, "Token is invalid");
            } else {
                authorities = (String) body.get("authorities");
            }

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();

            Arrays.asList(authorities.split(" "))
                    .forEach(a -> simpleGrantedAuthorities.add(new SimpleGrantedAuthority(a)));

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (ExpiredJwtException e) {

            throw new JwtException("Token is expired");

        } catch (JwtException e){

            throw new JwtException(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
