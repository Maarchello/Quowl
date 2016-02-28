package com.quowl.quowl.web.controllers.signinup;

import com.quowl.quowl.service.signinup.SecurityService;
import com.quowl.quowl.utils.ExecutionStatus;
import com.quowl.quowl.web.beans.JsonResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SecurityController {
    private final Logger log = LoggerFactory.getLogger(SecurityController.class);

    @Inject private SecurityService securityService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonResultBean login(@RequestParam("name") String name, @RequestParam("password") String password, HttpServletResponse response) {
        try {
            log.info("Trying login user");
            securityService.login(name, password, response);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return JsonResultBean.failure(ExecutionStatus.Invalid_user_data.toString());
        }
        return JsonResultBean.success();
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletResponse response) {
        securityService.logout(response);
        return "redirect:/";
    }

}
