package com.quowl.quowl.web.beans.user;


import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.web.beans.WebBean;

public class CurrentUserBean implements WebBean<User> {

    private Long id;
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void copyDataFromDomain(User obj) {
        setId(obj.getId());
        setNickname(obj.getNickname());
    }

    @Override
    public void copyDataToDomain(User obj) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (o.getClass() == getClass()) {
            CurrentUserBean that = (CurrentUserBean) o;
            if (id != null ? !id.equals(that.id) : that.id != null) return false;
            return nickname != null ? nickname.equals(that.nickname) : that.nickname == null;

        } else if (o.getClass() == UserBean.class) {
            UserBean that = (UserBean) o;
            if (id != null ? !id.equals(that.getId()) : that.getId() != null) return false;
            return nickname != null ? nickname.equals(that.getNickname()) : that.getNickname() == null;

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
}
