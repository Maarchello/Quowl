package com.quowl.quowl.web.controllers.account;

import com.quowl.quowl.domain.logic.user.Subscribe;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.repository.user.SubscribeRepository;
import com.quowl.quowl.service.notification.NotificationService;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.utils.ExecutionStatus;
import com.quowl.quowl.utils.SecurityUtils;
import com.quowl.quowl.web.beans.JsonResultBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@Controller
public class SubscribeController {
    @Inject private UserService userService;
    @Inject private SubscribeRepository subscribeRepository;
    @Inject private NotificationService notificationService;

    private final static String SUBSCRIBE_NOTIFICATION = "На ваши обновления подписан(а)";

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    @ResponseBody
    public JsonResultBean subscribe(@RequestParam("following") Long following) {
        if (following == null) {
            return JsonResultBean.failure("");
        }
        User from = userService.getByNickname(SecurityUtils.getCurrentLogin());
        Long follower = userService.getCurrentUser().getId();
        Subscribe subscribe = new Subscribe();
        subscribe.setFollowing(following);
        subscribe.setFollower(follower);
        try {
            subscribeRepository.save(subscribe);
        } catch (DataIntegrityViolationException e) {
            subscribeRepository.delete(subscribeRepository.findByFollowerAndFollowing(follower, following));
            return JsonResultBean.success("Decrement");
        }

        User to = userService.findOne(following);
        notificationService.createNotify(from.getNickname(), to, SUBSCRIBE_NOTIFICATION);
        return JsonResultBean.success("Increment");
    }

}
