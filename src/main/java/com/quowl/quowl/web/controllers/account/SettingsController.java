package com.quowl.quowl.web.controllers.account;

import com.quowl.quowl.domain.logic.user.ProfileInfo;
import com.quowl.quowl.service.account.ProfileService;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.web.beans.user.ProfileBean;
import com.quowl.quowl.web.beans.user.UserBean;
import com.quowl.quowl.web.controllers.account.validation.SettingsValidator;
import com.quowl.quowl.web.controllers.base.BaseController;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

@Controller
public class SettingsController extends BaseController {

    @Inject
    private UserService userService;
    @Inject
    private ProfileService profileService;

    @RequestMapping(value = "/settings")
    public String settings(Device device, Model model) {
        UserBean userBean = userService.getUserBean();
        ProfileBean profileBean = userBean.getProfileBean();

        model.addAttribute("profileBean", profileBean);

        return "account/settings";
    }

    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    public String update(@ModelAttribute(value = "profileBean") ProfileBean profileBean, Model model) {

        ProfileBean receivedProfile = profileBean;

        boolean isValid = SettingsValidator.isValid(receivedProfile);

        if (isValid) {
            ProfileInfo profileInfo = new ProfileInfo();
            receivedProfile.copyDataToDomain(profileInfo);
            profileService.save(profileInfo);
        } else {
            model.addAttribute("error", "Имя не может быть пустым.");
        }

        return "account/settings";
    }
}
