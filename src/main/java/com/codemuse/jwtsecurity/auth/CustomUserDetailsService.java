package com.codemuse.jwtsecurity.auth;

import com.codemuse.jwtsecurity.dto.CustomUserDetails;
import com.codemuse.jwtsecurity.entity.User;
import com.codemuse.jwtsecurity.mapper.Mapper;
import com.codemuse.jwtsecurity.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Username %s not found", username)));

        CustomUserDetails customUserDetails = Mapper.toCustomUserDetails(user);

        try {
            new AccountStatusUserDetailsChecker().check(customUserDetails);
        } catch (AccountStatusException e) {
            log.error("Could not authenticate user", e);
            throw new RuntimeException(e.getMessage());
        }

        return customUserDetails;
    }
}
