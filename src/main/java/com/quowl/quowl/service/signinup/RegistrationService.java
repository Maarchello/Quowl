package com.quowl.quowl.service.signinup;

import com.quowl.quowl.domain.logic.user.ProfileInfo;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.repository.user.ProfileRepository;
import com.quowl.quowl.repository.user.UserRepository;
import com.quowl.quowl.utils.ExecutionStatus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
public class RegistrationService {
    private final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    @Inject private UserRepository userRepository;
    @Inject private ProfileRepository profileRepository;
    @Inject private PasswordEncoder passwordEncoder;

    @Transactional
    public boolean registerUser(String username, String password, String email) {

        ProfileInfo profileInfo = new ProfileInfo();
        profileInfo = profileRepository.save(profileInfo);

        User newUser = new User();
        password = passwordEncoder.encode(password);
        username = username.toLowerCase();
        newUser.setPassword(password);
        newUser.setNickname(username);
        newUser.setEmail(email);
        newUser.setProfileInfo(profileInfo);
        userRepository.save(newUser);

        log.info("Registration new user completed successfully!");
        return true;
    }




}
