package com.quowl.quowl.repository.notification;

import com.quowl.quowl.domain.system.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n where n.to.id = ?1 and n.seen = false")
    List<Notification> findAllUnread(Long userId);

    @Query("select n from Notification n where n.to.id = ?1 and n.seen = false order by n.createdDate desc")
    List<Notification> findLast5Unread(Long userId, Pageable pageable);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query("update Notification n set n.seen = ?1 where n.to.id = ?2")
    void setTrueNotificationSeen(boolean status, Long userId);

}
