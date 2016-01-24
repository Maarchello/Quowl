package com.quowl.quowl.domain.quote;

import com.quowl.quowl.domain.base.AuditingEntity;
import com.quowl.quowl.domain.user.User;

import javax.persistence.*;

@Entity
@Table(name = "quote")
public class Quote extends AuditingEntity {

    @Column(name = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Quote() {}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
