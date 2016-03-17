package com.quowl.quowl.web.controllers.account;

import com.quowl.quowl.domain.logic.user.ProfileInfo;
import com.quowl.quowl.service.account.ProfileService;
import com.quowl.quowl.service.system.FileStorageService;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.utils.SecurityUtils;
import com.quowl.quowl.web.beans.user.CurrentUserBean;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is controller for {@link ProfileService}
 * and for all settings files at all. It sets attributes
 * for clients requests and receives attributes if
 * client sends it.
 *
 * @see #settings(Device, Model)
 * @see #update(ProfileBean, Model)
 * @see BaseController
 */
@Controller
public class SettingsController extends BaseController {

    @Inject
    private UserService userService;
    @Inject
    private ProfileService profileService;
    @Inject
    private FileStorageService fileStorageService;
    @Inject
    private SettingsValidator settingsValidator;

    @ModelAttribute
    private void setContext(Model model) {
        CurrentUserBean currentUserBean = userService.getCurrentUser();

        model.addAttribute("currentUser", currentUserBean);
    }

    /**
     * Sends Profile Bean to client.
     *
     * @param device
     * @param model  is used to add attributes.
     * @return the path to required view.
     */
    @RequestMapping(value = "/settings")
    public String settings(Device device, Model model) {
        UserBean userBean = userService.getUserBean();
        ProfileBean profileBean = userBean.getProfileBean();

        model.addAttribute("profileBean", profileBean);

        return "account/settings";
    }

    /**
     * Receives settings attributes from client.
     *
     * @param profileBean the received profile bean with profiles settings.
     * @param model       is used to return answer to client.
     * @return the path to required view.
     */
    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    public String update(@ModelAttribute(value = "profileBean") ProfileBean profileBean, Model model) {
        boolean isValid = settingsValidator.isValid(profileBean);

        if (isValid) {
            ProfileInfo profileInfo = new ProfileInfo();
            profileBean.copyDataToDomain(profileInfo);
            profileService.save(profileInfo);
        } else {
            model.addAttribute("error", "Имя не может быть пустым.");
        }

        return "redirect:/settings";
    }

    @RequestMapping(value = "/settings/avatar/change", method = RequestMethod.POST)
    public String uploadImage(@RequestParam("image") MultipartFile file) {
        String nickname = SecurityUtils.getCurrentLogin();
        boolean alright = fileStorageService.uploadImage(file, nickname);

        return "redirect:/settings";
    }

    @RequestMapping(value = "/settings/password/change", method = RequestMethod.GET)
    public String changePassword(Model model) {
        return "account/changepassword";
    }

    @RequestMapping(value = "/settings/password/change", method = RequestMethod.POST)
    public String changePassword(@RequestParam("current") String current, @RequestParam("new") String newPassword, @RequestParam("verify") String verify, HttpServletResponse response, HttpServletRequest request, Model model) {
        boolean success = settingsValidator.isValidPassword(current, newPassword, verify);

        if (success) {
            userService.changePassword(newPassword, response, request);
        } else {
            model.addAttribute("fail", "Change password failed. Make sure you typed correctly.");
            return "account/changepassword";
        }
        return "redirect:/settings";
    }
}
