package com.quowl.quowl.web.controllers.account;

import com.quowl.quowl.web.controllers.base.BaseController;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SettingsController extends BaseController {

    @RequestMapping(value = "/settings")
    public String settings(Device device, Model model) {


        return "account/settings";
    }

}
