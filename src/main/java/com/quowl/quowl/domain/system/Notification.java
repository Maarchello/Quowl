package com.quowl.quowl.domain.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quowl.quowl.domain.base.AuditingEntity;
import com.quowl.quowl.domain.logic.user.User;

import javax.persistence.*;

@Entity
@Table(indexes = {
        @Index(name = "IDX_USER", columnList = "to_user")
})
public class Notification extends AuditingEntity {

    @Column
    private String message;

    @Column
    private boolean seen;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user")
    private User to;

    @Column(length = 255)
    private String fromUser;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }
}
