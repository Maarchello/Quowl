package com.quowl.quowl.web.beans.user;


import com.quowl.quowl.domain.logic.user.ProfileInfo;

public class ProfileBean {

    private String firstName;
    private String lastName;

    public ProfileBean(ProfileInfo profileInfo) {
        this.firstName = profileInfo.getFirstName();
        this.lastName = profileInfo.getLastName();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
