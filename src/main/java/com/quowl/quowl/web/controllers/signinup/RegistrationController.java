package com.quowl.quowl.web.controllers.signinup;

import com.quowl.quowl.service.signinup.RegistrationService;
import com.quowl.quowl.utils.ExecutionStatus;
import com.quowl.quowl.web.beans.system.JsonResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Controller
public class RegistrationController {
    //LocaleResolver
    //HttpServletRequest
    //Вытащить локаль
    //Сделать валидатор компонент, инжект messageSource
    //Там проверки паролей, и тд
    //метод getMessage
    private final Logger log = LoggerFactory.getLogger(RegistrationController.class);
    @Inject private RegistrationService registrationService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public JsonResultBean registration(@RequestParam(value = "username", required = true) String username, @RequestParam(value = "password", required = true) String password, @RequestParam(value = "email", required = true) String email) {

        try {
            String result = registrationService.registerUser(username, password, email);
            if (!result.equals("OK")) {
                return JsonResultBean.failure(result);
            }
        } catch (DataIntegrityViolationException e) {
            return JsonResultBean.failure(ExecutionStatus.This_user_name_already_registered.toString());
        }
        return JsonResultBean.success();
    }

}
