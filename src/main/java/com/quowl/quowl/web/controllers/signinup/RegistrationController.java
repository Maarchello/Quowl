package com.quowl.quowl.web.controllers.signinup;

import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.service.signinup.RegistrationService;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.utils.HashUtils;
import com.quowl.quowl.web.beans.system.JsonResultBean;
import com.quowl.quowl.web.controllers.signinup.validation.SignupValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class RegistrationController {
    private final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    @Inject
    private LocaleResolver localeResolver;
    @Inject
    private RegistrationService registrationService;
    @Inject
    private SignupValidator validator;
    @Inject
    private MessageSource messageSource;
    @Inject
    private UserService userService;


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public JsonResultBean registration(@RequestParam(value = "username", required = true) String username,
                                       @RequestParam(value = "password", required = true) String password,
                                       @RequestParam(value = "email", required = true) String email,
                                       HttpServletRequest request) {

        Locale locale = localeResolver.resolveLocale(request);
        JsonResultBean resultBean = validator.validate(username, email, locale);

        if (resultBean.getResult()){
            boolean result = registrationService.registerUser(username, password, email, request);
            if (!result) {
                return JsonResultBean.failure(messageSource.getMessage("", null, locale));
            }
        }

        return resultBean;
    }

    @RequestMapping(value = "/access/user/activating", method = RequestMethod.GET)
    public String activate(@RequestParam(value = "r", required = true) String hash) {
        Long userId = HashUtils.decodeUserID(hash);
        User user = userService.findOne(userId);
        user.setActivated(true);
        userService.save(user);
        return "redirect:/";
    }


}
