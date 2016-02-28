package com.quowl.quowl.web.controllers.signinup;

import org.springframework.mobile.device.Device;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Device device) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal().equals("anonymousUser")){
            if (device.isMobile() || device.isTablet()) {
                return "mobile/index";
            }
            return "index";
        } else {
            return "redirect:/feed";
        }

    }

}
