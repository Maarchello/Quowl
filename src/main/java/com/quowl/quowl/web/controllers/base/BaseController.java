package com.quowl.quowl.web.controllers.base;

import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

    protected boolean isMobile(Device device) {
        return device.isMobile() || device.isTablet();
    }

}
