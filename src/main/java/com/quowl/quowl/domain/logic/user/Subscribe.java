package com.quowl.quowl.domain.logic.user;

import com.quowl.quowl.domain.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "subs", indexes = {
        @Index(name = "IDX_FOLLOWER", columnList = "follower"),
        @Index(name = "IDX_FOLLOWING", columnList = "following"),
        @Index(name = "IDX_FOLLOWING_FOLLOWER", columnList = "following,follower")
})
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
