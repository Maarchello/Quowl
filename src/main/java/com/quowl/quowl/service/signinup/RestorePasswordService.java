package com.quowl.quowl.service.signinup;

import com.quowl.quowl.service.system.MailService;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.utils.HashUtils;
import com.quowl.quowl.web.beans.system.JsonResultBean;
import com.quowl.quowl.web.controllers.signinup.validation.SignupValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.context.Context;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by nllsd on 31.03.2016.
 */
@Service
public class RestorePasswordService {
    @Inject
    private MailService mailService;
    @Inject
    private UserService userService;
    @Inject
    private MessageSource messageSource;
    @Inject
    private LocaleResolver localeResolver;

    private static String passwordRecovery = "http://localhost:8080/precovery/";

    public JsonResultBean sendResetPasswordLink(String email, HttpServletRequest request) {
        Locale locale = localeResolver.resolveLocale(request);

        boolean isEmailValid = isEmailValid(email);
        String url = null;

        if (isEmailValid){
            url = generateRecoveryLink(email);
            Context context = new Context(locale);
            context.setVariable("recoveryUrl", url);
            mailService.sendEmail("recovery", email, context);
        }

        return sendResult(isEmailValid, locale);
    }

    private String generateRecoveryLink(String email){
        Long userId = userService.findIdByEmail(email);
        return passwordRecovery + HashUtils.encodeUserID(userId);
    }

    private boolean isEmailValid(String email) {

        if (!EmailValidator.getInstance().isValid(email)) {
            return false;
        } else if (!userService.existsEmail(email)) {
            return false;
        } else{
            return true;
        }


    }

    private JsonResultBean sendResult(boolean isEmailValid, Locale locale) {
        if (!isEmailValid) {
            return JsonResultBean.failure(messageSource.getMessage("emailNotExists", null, locale));
        } else {

            return  JsonResultBean.success(messageSource.getMessage("checkMailBox", null, locale));
        }
    }
}
