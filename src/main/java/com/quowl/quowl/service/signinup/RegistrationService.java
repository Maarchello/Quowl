package com.quowl.quowl.service.signinup;

import com.quowl.quowl.domain.logic.user.ProfileInfo;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.repository.user.ProfileRepository;
import com.quowl.quowl.repository.user.UserRepository;
import com.quowl.quowl.service.system.MailService;
import com.quowl.quowl.utils.ExecutionStatus;
import com.quowl.quowl.utils.HashUtils;
import org.apache.commons.lang3.StringUtils;
import org.hashids.Hashids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
@PropertySource("classpath:properties/main.properties")
@Service
public class RegistrationService {
    private final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    @Resource
    private Environment env;

    @Inject private UserRepository userRepository;
    @Inject private ProfileRepository profileRepository;
    @Inject private PasswordEncoder passwordEncoder;
    @Inject private MailService mailService;
    @Inject private LocaleResolver localeResolver;

    private static final String URL_ACTIVATE = "access/user/activating?r=";

    @Transactional
    public boolean registerUser(String username, String password, String email, HttpServletRequest request) {
        try {
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

            mailService.sendEmail("activate", email, getContext(email, request));
        } catch (Exception e) {
            log.error("Some error: " + e.getMessage());
            return false;
        }

        log.info("Registration new user completed successfully!");
        return true;
    }

    private Context getContext(String email, HttpServletRequest request) {
        String url = env.getRequiredProperty("baseUrl");
        url += URL_ACTIVATE + generateActivatingUrl(email);

        Locale locale = localeResolver.resolveLocale(request);
        Context context = new Context();
        context.setLocale(locale);
        context.setVariable("activateUrl", url);

        return context;
    }

    private String generateActivatingUrl(String email) {
        Long id = userRepository.findIdByEmail(email);
        return HashUtils.encodeUserID(id);
    }

}
