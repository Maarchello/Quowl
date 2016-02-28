package com.quowl.quowl.domain.logic.user;

import com.quowl.quowl.domain.base.BaseEntity;
import com.quowl.quowl.web.controllers.base.BaseController;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "subs")
public class Subscribe extends BaseEntity {

    @Column
    private Long following;

    @Column
    private Long follower;

    public Long getFollower() {
        return follower;
    }

    public void setFollower(Long follower) {
        this.follower = follower;
    }

    public Long getFollowing() {
        return following;
    }

    public void setFollowing(Long following) {
        this.following = following;
    }
}
