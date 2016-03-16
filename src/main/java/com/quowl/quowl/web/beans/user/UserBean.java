package com.quowl.quowl.web.beans.user;


import com.quowl.quowl.domain.logic.user.Gender;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.web.beans.WebBean;

import java.util.List;

public class UserBean implements WebBean<User> {

    private Long id;
    private String nickname;
    private String bookName;
    private String authorName;
    private Long countReadBooks;
    private Long countQuotes;
    private ProfileBean profileBean;
    private List<Long> followers;
    private List<Long> following;
    private Gender gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Long getCountReadBooks() {
        return countReadBooks;
    }

    public void setCountReadBooks(Long countReadBooks) {
        this.countReadBooks = countReadBooks;
    }

    public Long getCountQuotes() {
        return countQuotes;
    }

    public void setCountQuotes(Long countQuotes) {
        this.countQuotes = countQuotes;
    }

    public ProfileBean getProfileBean() {
        return profileBean;
    }

    public void setProfileBean(ProfileBean profileBean) {
        this.profileBean = profileBean;
    }

    @Override
    public void copyDataFromDomain(User obj) {
        setId(obj.getId());
        setNickname(obj.getNickname());
        setBookName(obj.getBookName());
        setAuthorName(obj.getAuthorName());
    }

    @Override
    public void copyDataToDomain(User obj) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (o.getClass() == getClass()) {
            UserBean userBean = (UserBean) o;
            if (id != null ? !id.equals(userBean.id) : userBean.id != null) return false;
            return nickname != null ? nickname.equals(userBean.nickname) : userBean.nickname == null;

        } else if (o.getClass() == CurrentUserBean.class) {
            CurrentUserBean userBean = (CurrentUserBean) o;

            if (id != null ? !id.equals(userBean.getId()) : userBean.getId() != null) return false;
            return nickname != null ? nickname.equals(userBean.getNickname()) : userBean.getNickname() == null;
        } else {
            return false;
        }


    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        return result;
    }

    public List<Long> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Long> followers) {
        this.followers = followers;
    }

    public List<Long> getFollowing() {
        return following;
    }

    public void setFollowing(List<Long> following) {
        this.following = following;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
