package com.quowl.quowl.domain.system;

import com.quowl.quowl.domain.base.AuditingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "comment",
        indexes = {
        @Index(name = "IDX_QUOTE", columnList = "quoteId")
})
public class Comment extends AuditingEntity {

    @Column
    private String message;

    @Column
    private Long quoteId;

    @Column
    private Long userId;

    @Column
    private String nickname;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
