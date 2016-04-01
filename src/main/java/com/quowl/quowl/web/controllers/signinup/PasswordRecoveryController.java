package com.quowl.quowl.web.controllers.signinup;

import com.quowl.quowl.service.signinup.RecoveryPasswordService;
import com.quowl.quowl.web.beans.system.JsonResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by nllsd on 01.04.2016.
 */
@Controller
public class PasswordRecoveryController {

    @Inject
    private RecoveryPasswordService recoveryPasswordService;

    @RequestMapping(value = "/restore", method = RequestMethod.POST)
    @ResponseBody
    public JsonResultBean restorePassword(@RequestParam(value = "restoreEmail", required = true) String email,
                                          HttpServletRequest request) {

        return recoveryPasswordService.sendResetPasswordLink(email, request);
    }

    @RequestMapping(value = "/precovery")
    public String setNewPassword(@RequestParam("r") String recoveryLink){

        return "security/passwordRecovery";
    }

}
