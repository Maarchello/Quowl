package com.quowl.quowl.web.controllers.account.validation;

import com.quowl.quowl.domain.logic.user.Gender;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.utils.SecurityUtils;
import com.quowl.quowl.web.beans.user.ProfileBean;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Optional;

/**
 * This class is provided to validate
 * user settings.
 *
 * @see #isValid(ProfileBean)
 * @see ProfileBean
 *
 * @author nllsdfx
 */
@Component
public final class SettingsValidator {
    private final Logger log = LoggerFactory.getLogger(SettingsValidator.class);

    @Inject private UserService userService;
    @Inject private PasswordEncoder passwordEncoder;

    private SettingsValidator() {
    }

    /**
     * Checks whether user profile settings are correct.
     *
     * @param bean bean to check.
     * @return <code>true</code> if settings are correct,
     * <code>false</code> otherwise.
     */
    public boolean isValid(ProfileBean bean) {

        boolean isCorrect = false;

        if (bean == null) {
            return isCorrect;
        }

        String firstName = bean.getFirstName();
        String lastName = bean.getLastName();

        boolean isFirstNameValid = isStringValid(firstName);
        boolean isLastNameValid = isStringValid(lastName);

        isCorrect = isFirstNameValid && isLastNameValid;

        return isCorrect;
    }

    /**
     * Checks whether the given string is null or empty.
     *
     * @param string string to check.
     * @return <code>true</code> if string is not null or empty,
     * <code>false</code> otherwise.
     */
    private boolean isStringValid(String string) {

        return string != null && !string.isEmpty();
    }

    public boolean isValidPassword(String current, String newPassword, String verify) {
        log.info("Start validation password for empty.");
        if (!(StringUtils.isBlank(current) && StringUtils.isBlank(newPassword) && StringUtils.isBlank(verify))) {
            log.info("Password not empty. OK.");
            return isEqualsOfNewAndVerify(current, newPassword, verify);
        }
        log.error("Password fields are empty. ERROR.");
        return false;
    }

    private boolean isEqualsOfNewAndVerify(String current, String newPassword, String verify) {
        log.info("Start validation fields `newPassword` and `verify`");
        if (newPassword.equals(verify)) {
            log.info("Fields equals. OK.");

            String username = SecurityUtils.getCurrentLogin();
            User user = userService.getByNickname(username);
            String reallyCurrentPassword = user.getPassword();
            return isSame(reallyCurrentPassword, current);
        }
        log.error("Fields are not equals. ERROR.");
        return false;
    }

    private boolean isSame(String current, String passwordFromForm) {
        log.info("Start validation current password for equals.");
        boolean matches = passwordEncoder.matches(passwordFromForm, current);
        if (matches) {
            log.info("Passwords match! We can change password. OK.");
            return true;
        }

        log.error("Field `current` doesn't match with real password of the user.");
        return false;
    }



}
