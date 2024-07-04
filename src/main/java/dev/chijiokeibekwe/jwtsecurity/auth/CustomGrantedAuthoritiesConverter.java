package dev.chijiokeibekwe.jwtsecurity.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Slf4j
public class CustomGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        String authorities = jwt.getClaim("authorities");
        Arrays.asList(authorities.split(" ")).forEach(a -> grantedAuthorities.add(new SimpleGrantedAuthority(a)));

        return grantedAuthorities;
    }
}
