package com.quowl.quowl.service.signinup;

import com.quowl.quowl.domain.system.PasswordRecovery;
import com.quowl.quowl.repository.system.PasswordRecoveryRepository;
import com.quowl.quowl.service.system.MailService;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.utils.HashUtils;
import com.quowl.quowl.web.beans.system.IService;
import com.quowl.quowl.web.beans.system.JsonResultBean;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.context.Context;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 * Created by nllsd on 31.03.2016.
 */
@Service
public class RecoveryPasswordService implements IService<PasswordRecovery, Long> {
    @Inject
    private MailService mailService;
    @Inject
    private UserService userService;
    @Inject
    private MessageSource messageSource;
    @Inject
    private LocaleResolver localeResolver;
    @Inject
    private PasswordRecoveryRepository recoveryRepository;

    private static String passwordRecoveryUrl = "http://localhost:8080/precovery?r=";

    private static final long EXPIRED = 1000 * 60 * 30;


    public JsonResultBean tryToResetPassword(String recoveryLink, HttpServletRequest request) {
        PasswordRecovery passwordRecovery = findByRecoveryLink(recoveryLink);
        Locale locale = localeResolver.resolveLocale(request);

        if (!checkExpiredLinkTime(passwordRecovery)) {
            return JsonResultBean.failure(messageSource.getMessage("linkExpired", null, locale));

        } else {
            return JsonResultBean.success();
        }
    }

    private boolean checkExpiredLinkTime(PasswordRecovery passwordRecovery) {

        long currentTime = System.currentTimeMillis();
        long savedTime = passwordRecovery.getCreationTime();
        long difference = currentTime - savedTime;

        return difference < EXPIRED;
    }


    private JsonResultBean setNewPassword(String password) {


        return new JsonResultBean();
    }

    public JsonResultBean sendResetPasswordLink(String email, HttpServletRequest request) {
        Locale locale = localeResolver.resolveLocale(request);

        boolean isEmailValid = isEmailValid(email);
        String url = null;

        if (isEmailValid) {
            PasswordRecovery passwordRecovery = new PasswordRecovery();
            Context context = new Context(locale);

            Long encodingTime = System.currentTimeMillis();
            url = generateRecoveryLink(email, encodingTime);

            passwordRecovery.setCreationTime(encodingTime);
            passwordRecovery.setUrl(url);
            save(passwordRecovery);

            url = passwordRecoveryUrl + url;
            context.setVariable("recoveryUrl", url);
            mailService.sendEmail("recovery", email, context);
        }

        return sendResult(isEmailValid, locale);
    }

    private String generateRecoveryLink(String email, Long creationDate) {
        Long userId = userService.findIdByEmail(email);
        return HashUtils.encodeUserID(userId + creationDate);
    }

    private boolean isEmailValid(String email) {

        if (!EmailValidator.getInstance().isValid(email)) {
            return false;
        } else if (!userService.existsEmail(email)) {
            return false;
        } else {
            return true;
        }


    }

    private JsonResultBean sendResult(boolean isEmailValid, Locale locale) {
        if (!isEmailValid) {
            return JsonResultBean.failure(messageSource.getMessage("emailNotExists", null, locale));
        } else {

            return JsonResultBean.success(messageSource.getMessage("checkMailBox", null, locale));
        }
    }

    public PasswordRecovery findByRecoveryLink(String recoveryLink) {
        return recoveryRepository.findByRecoveryLink(recoveryLink);
    }

    @Override
    public PasswordRecovery save(PasswordRecovery object) {
        return recoveryRepository.save(object);
    }

    @Override
    public void delete(PasswordRecovery object) {
        recoveryRepository.delete(object);
    }

    @Override
    public void delete(Long l) {
        recoveryRepository.delete(l);
    }

    @Override
    public List<PasswordRecovery> findAll() {
        return (List<PasswordRecovery>) recoveryRepository.findAll();
    }

    @Override
    public PasswordRecovery findOne(Long l) {
        return recoveryRepository.findOne(l);
    }

    @Override
    public boolean exists(Long l) {
        return recoveryRepository.exists(l);
    }
}
