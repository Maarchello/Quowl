package com.quowl.quowl.service.signinup;

import com.quowl.quowl.domain.logic.user.ProfileInfo;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.repository.user.ProfileRepository;
import com.quowl.quowl.repository.user.UserRepository;
import com.quowl.quowl.utils.ExecutionStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
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
    public String registerUser(String username, String password, String email) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(email)) {
            log.warn("User data empty or null");
            return ExecutionStatus.User_data_empty.toString();
        }
        if (!isValidateEmail(email)) {
            log.warn("Wrong email address");
            return ExecutionStatus.Wrong_email_address.toString();
        }
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
        return ExecutionStatus.OK.toString();
    }


    private boolean isValidateEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

}
