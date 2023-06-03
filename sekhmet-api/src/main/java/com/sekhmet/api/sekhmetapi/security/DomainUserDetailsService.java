package com.sekhmet.api.sekhmetapi.security;

import com.sekhmet.api.sekhmetapi.domain.User;
import com.sekhmet.api.sekhmetapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String phonenumber) {
        log.debug("Authenticating {}", phonenumber);
        return userRepository
                .findUserByPhoneNumber(phonenumber)
                .map(user -> createSpringSecurityUser(phonenumber, user))
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + phonenumber + " was not found in the database"));

    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String phonenumber, User user) {
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + phonenumber + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = user
                .getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getPhoneNumber(), "", grantedAuthorities);
    }
}
