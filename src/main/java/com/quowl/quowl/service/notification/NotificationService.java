package com.quowl.quowl.service.notification;

import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.domain.system.Notification;
import com.quowl.quowl.repository.notification.NotificationRepository;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.web.beans.IService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class NotificationService implements IService<Notification, Long> {
    @Inject
    private NotificationRepository notificationRepository;
    @Inject
    private UserService userService;

    public void createNotify(String from, User to, String message) {
        if (to.getNickname().equals(from)) {
            return;
        }
        Notification notification = new Notification();
        notification.setFromUser(from);
        notification.setTo(to);
        notification.setMessage(message);
        notification.setSeen(false);
        save(notification);
    }

    public void createNotify(String from, List<Long> followers, String message) {
        Notification notification = new Notification();
        notification.setFromUser(from);
        notification.setMessage(message);
        notification.setSeen(false);

        for (Long follower : followers) {
            User user = userService.findOne(follower);
            notification.setTo(user);
            save(notification);
        }
    }

    public List<Notification> getAllUnread(Long userId) {

        return notificationRepository.findAllUnread(userId);
    }

    public List<Notification> getLastUnread(Long userId) {
        return notificationRepository.findLast5Unread(userId, new PageRequest(0, 5));
    }

    public void setTrueNotificationSeen(Long userId) {
        notificationRepository.setTrueNotificationSeen(true, userId);
    }

    @Override
    public Notification save(Notification object) {
        return notificationRepository.save(object);
    }

    @Override
    public void delete(Notification object) {
        notificationRepository.delete(object);
    }

    @Override
    public void delete(Long aLong) {
        notificationRepository.delete(aLong);
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification findOne(Long aLong) {
        return notificationRepository.findOne(aLong);
    }

    @Override
    public boolean exists(Long aLong) {
        return notificationRepository.exists(aLong);
    }
}
