package com.quowl.quowl.web.controllers.signinup;

import com.quowl.quowl.utils.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Controller
public class IndexController {
    private final Logger log = LoggerFactory.getLogger(IndexController.class);


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Device device, HttpServletRequest request, HttpServletResponse response) {

        if (!CookieUtils.existCookie(request)) {
            String language = request.getHeader("Accept-Language").substring(0, 2);
            String locale = language.equals("ru") ? language : "en";
            CookieUtils.setLocaleCookie(response, locale);
            return "redirect:/";
        }

        log.info("Into index controller");
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
