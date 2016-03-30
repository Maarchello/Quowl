package com.quowl.quowl.web.controllers.signinup.validation;

import com.quowl.quowl.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import com.quowl.quowl.web.beans.system.JsonResultBean;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.inject.Inject;
import java.util.Locale;

/**
 * This class provides user registration validation.
 * It checks if user email is unique and correct and if
 * userName is unique. Also it returns error or success
 * message in the needed language according to user locale.
 *
 * @author nllsdfx
 * @author Marchello
 * @see #validate(String, String, Locale)
 * @see MessageSource
 * @see UserService
 */
@Component
public class SignupValidator {

    @Inject
    private MessageSource messageSource;

    @Inject
    private UserService userService;

    /**
     * Validates user registration data.
     *
     * @param userName userName to check.
     * @param email    email to check.
     * @param locale   user's selected language
     * @return JsonResult bean with error message or
     * with success message if user data was correct.
     *
     * @see #isEmailValid(String)
     * @see #emailExists(String)
     * @see #userNameExists(String)
     */
    public JsonResultBean validate(String userName, String email, Locale locale) {
        if (!isEmailValid(email)) {
            return JsonResultBean.failure(messageSource.getMessage("wrongEmail", null, locale));
        } else if (!emailExists(email)) {
            return JsonResultBean.failure(messageSource.getMessage("emailExists", null, locale));

        } else if (!userNameExists(userName)) {
            return JsonResultBean.failure(messageSource.getMessage("nameExists", null, locale));
        } else {
            return JsonResultBean.success(messageSource.getMessage("regSuccess", null, locale));
        }


    }

    /**
     * Checks whether user email is valid.
     *
     * @param email email to check.
     * @return <code>true</code> if email is correct,
     * <code>false</code> otherwise.
     */
    private boolean isEmailValid(String email) {

        return EmailValidator.getInstance().isValid(email);
    }

    /**
     * Checks if user with the given userName exists.
     *
     * @param userName userName to check.
     * @return <code>true</code> if user exists,
     * <code>false</code> otherwise.
     */
    private boolean userNameExists(String userName) {

        return userService.existsUserName(userName);
    }

    /**
     * Checks whether user email is unique.
     *
     * @param email to check.
     * @return <code>true</code> if email exists,
     * <code>false</code> otherwise.
     */
    private boolean emailExists(String email) {
        return userService.existsEmail(email);
    }


}
