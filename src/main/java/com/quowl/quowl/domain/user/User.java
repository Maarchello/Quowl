package com.quowl.quowl.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quowl.quowl.domain.base.AuditingEntity;
import com.quowl.quowl.domain.quote.Quote;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "user")
public class User extends AuditingEntity {

    @Size(max = 100)
    @Column(name = "nickname", length = 100)
    private String nickname;

    @Size(max = 100)
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @JsonIgnore
    @Size(min = 6, max = 100)
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Size(min = 2, max = 5)
    @Column(name = "langKey", nullable = true, length = 5)
    private String langKey;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    private ProfileInfo profileInfo;

    @OneToMany(mappedBy = "user")
    private List<Quote> quotes;

    public User() {}

    public ProfileInfo getProfileInfo() {
        return profileInfo;
    }

    public void setProfileInfo(ProfileInfo profileInfo) {
        this.profileInfo = profileInfo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getName() {
        return nickname;
    }

    public void setName(String nickname) {
        this.nickname = nickname;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }
}
