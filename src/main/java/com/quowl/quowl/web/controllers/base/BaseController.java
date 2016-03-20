package com.quowl.quowl.web.controllers.base;

import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.domain.system.Notification;
import com.quowl.quowl.service.notification.NotificationService;
import com.quowl.quowl.service.system.FileStorageService;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.utils.SecurityUtils;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class BaseController {
    @Inject private FileStorageService fileStorageService;
    @Inject private UserService userService;
    @Inject private NotificationService notificationService;

    @ModelAttribute
    protected void setAvatar(Model model) throws IOException {
        long start = System.currentTimeMillis();
        User user = userService.getByNickname(SecurityUtils.getCurrentLogin());
        if (user != null) {
            model.addAttribute("myavatar", Base64.getEncoder().encodeToString(fileStorageService.getImage(user)));
            long finish = System.currentTimeMillis();
            System.out.println("Amazon S3 execute time:" + (finish - start) + "ms");
            List<Notification> notifications = notificationService.getAllUnread(user.getId());
            model.addAttribute("notify", notifications.size());
        }
    }

    protected boolean isMobile(Device device) {
        return device.isMobile() || device.isTablet();
    }

}
