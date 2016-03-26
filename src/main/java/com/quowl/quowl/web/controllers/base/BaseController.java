package com.quowl.quowl.web.controllers.base;

import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.domain.system.Notification;
import com.quowl.quowl.service.notification.NotificationService;
import com.quowl.quowl.service.storage.StorageService;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.utils.SecurityUtils;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Controller
public class BaseController {
    @Inject private StorageService storageService;
    @Inject private UserService userService;
    @Inject private NotificationService notificationService;

    @ModelAttribute
    protected void setAvatar(Model model) throws IOException {
        User user = userService.getByNickname(SecurityUtils.getCurrentLogin());
        if (user != null) {
            model.addAttribute("myavatar", storageService.getAvatarUrl(user));
            List<Notification> notifications = notificationService.getAllUnread(user.getId());
            model.addAttribute("notify", notifications.size());
        }
    }

    protected boolean isMobile(Device device) {
        return device.isMobile() || device.isTablet();
    }

}
