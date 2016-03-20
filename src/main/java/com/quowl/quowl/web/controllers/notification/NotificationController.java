package com.quowl.quowl.web.controllers.notification;

import com.quowl.quowl.domain.system.Notification;
import com.quowl.quowl.service.notification.NotificationService;
import com.quowl.quowl.web.beans.JsonResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;

@Controller
public class NotificationController {
    @Inject private NotificationService notificationService;

    @RequestMapping(value = "notifications/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResultBean getNotifications(@PathVariable("id") String id) {
        Long userId = Long.valueOf(id);

        List<Notification> notifications = notificationService.getLastUnread(userId);
        return JsonResultBean.success(notifications);
    }

    @RequestMapping(value = "notification/seen/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResultBean setTrueNotificationSeen(@PathVariable("id") String id) {
        Long userId = Long.valueOf(id);
        notificationService.setTrueNotificationSeen(userId);

        return JsonResultBean.success();
    }

}
