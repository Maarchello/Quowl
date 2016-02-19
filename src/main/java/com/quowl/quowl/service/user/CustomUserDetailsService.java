package com.quowl.quowl.service.user;

import com.quowl.quowl.domain.logic.user.SecurityUser;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.repository.user.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component("customUserDetails")
public class CustomUserDetailsService implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Inject private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String nickname) throws UsernameNotFoundException {
        String lowerNickname = nickname.toLowerCase();
        User user = userRepository.findOneByNickname(lowerNickname);

        if (user == null) {
            log.warn("User with name " + lowerNickname + " not found in database.");
            throw new UsernameNotFoundException("Username " + lowerNickname + " not found");
        }

        return new SecurityUser(user);
    }
}
