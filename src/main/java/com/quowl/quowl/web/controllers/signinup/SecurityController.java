package com.quowl.quowl.web.controllers.signinup;

import com.quowl.quowl.service.signinup.SecurityService;
import com.quowl.quowl.utils.ExecutionStatus;
import com.quowl.quowl.web.beans.system.JsonResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Controller
public class SecurityController {
    private final Logger log = LoggerFactory.getLogger(SecurityController.class);

    @Inject
    private SecurityService securityService;
    @Inject
    private MessageSource messageSource;
    @Inject
    private LocaleResolver localeResolver;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonResultBean login(@RequestParam("name") String name, @RequestParam("password") String password,
                                HttpServletResponse response,
                                HttpServletRequest request) {

        Locale locale = localeResolver.resolveLocale(request);

        try {
            log.info("Trying login user");
            securityService.login(name, password, response);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return JsonResultBean.failure(messageSource.getMessage("wrongNameOrPassword", null, locale));
        }
        return JsonResultBean.success();
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletResponse response) {
        securityService.logout(response);
        return "redirect:/";
    }

}
