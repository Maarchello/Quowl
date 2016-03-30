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
 *
 */
@Component
public class SignupValidator {

    @Inject
    private MessageSource messageSource;

    @Inject
    private UserService userService;


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

    private boolean isEmailValid(String email) {

        return EmailValidator.getInstance().isValid(email);
    }

    private boolean userNameExists(String userName) {

        return userService.existsUserName(userName);
    }

    private boolean emailExists(String email) {
        return userService.existsEmail(email);
    }


}
